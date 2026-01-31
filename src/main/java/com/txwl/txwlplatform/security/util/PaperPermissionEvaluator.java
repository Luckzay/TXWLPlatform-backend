package com.txwl.txwlplatform.security.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txwl.txwlplatform.model.entity.Paper;
import com.txwl.txwlplatform.security.WebAuthenticationDetailsWithUserRoleId;
import com.txwl.txwlplatform.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("paperPermissionEvaluator")
public class PaperPermissionEvaluator {
    
    @Autowired
    private IPaperService paperService;
    
    public boolean hasPaperAccess(Authentication authentication, Long paperId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        try {
            // 从认证信息中获取用户角色ID
            Long userRoleId = extractUserRoleIdFromAuthentication(authentication);
            if (userRoleId == null) {
                return false;
            }
            
            // 获取试卷信息
            Paper paper = paperService.getPaperById(paperId);
            if (paper == null) {
                return false;
            }
            
            // 解析试卷的访问角色ID列表
            Set<Long> allowedRoleIds = parseAccessRoleIds(paper.getAccessRoleIds());
            System.out.println(allowedRoleIds.contains(userRoleId));
            // 检查用户角色是否在允许列表中
            return allowedRoleIds.contains(userRoleId);
            
        } catch (Exception e) {
            // 记录错误日志
            e.printStackTrace();
            return false;
        }
    }
    
    private Long extractUserRoleIdFromAuthentication(Authentication authentication) {
        // 尝试从Authentication的details中获取用户角色ID
        Object details = authentication.getDetails();
        if (details instanceof WebAuthenticationDetailsWithUserRoleId) {
            WebAuthenticationDetailsWithUserRoleId customDetails = (WebAuthenticationDetailsWithUserRoleId) details;
            return customDetails.getUserRoleId();
        }
        
        return null;
    }
    
    private Set<Long> parseAccessRoleIds(String accessRoleIdsJson) {
        // 解析JSON格式的角色ID列表
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Long> roleIds = objectMapper.readValue(accessRoleIdsJson, new TypeReference<List<Long>>() {});
            return new HashSet<>(roleIds);
        } catch (Exception e) {
            // 如果解析JSON失败，尝试解析逗号分隔的格式（兼容SQL中存储的格式 [1,2,3]）
            try {
                String cleaned = accessRoleIdsJson.replaceAll("[\\[\\]]", "");
                String[] parts = cleaned.split(",");
                Set<Long> roleIds = new HashSet<>();
                for (String part : parts) {
                    if (!part.trim().isEmpty()) {
                        roleIds.add(Long.parseLong(part.trim()));
                    }
                }
                return roleIds;
            } catch (Exception ex) {
                ex.printStackTrace();
                return Collections.emptySet();
            }
        }
    }
}