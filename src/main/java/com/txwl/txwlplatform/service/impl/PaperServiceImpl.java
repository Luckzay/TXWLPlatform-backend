package com.txwl.txwlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.txwl.txwlplatform.mapper.PaperMapper;
import com.txwl.txwlplatform.model.entity.Paper;
import com.txwl.txwlplatform.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public Page<Paper> getPapersPage(int pageNum, int pageSize) {
        Page<Paper> page = new Page<>(pageNum, pageSize);
        return paperMapper.selectPage(page, null);
    }

    @Override
    public List<Paper> getAllPapers() {
        return paperMapper.selectList(null);
    }

    @Override
    public Paper getPaperById(Long id) {
        return paperMapper.selectById(id);
    }

    @Override
    public void createPaper(Paper paper) {
        paperMapper.insert(paper);
    }

    @Override
    public void updatePaper(Paper paper) {
        paperMapper.updateById(paper);
    }

    @Override
    public void deletePaper(Long id) {
        paperMapper.deleteById(id);
    }
    
    @Override
    public boolean hasAccessToPaper(Long paperId, Long userRoleId) {
        Paper paper = getPaperById(paperId);
        if (paper == null || userRoleId == null) {
            return false;
        }
        
        // 解析试卷的访问角色ID列表
        Set<Long> allowedRoleIds = parseAccessRoleIds(paper.getAccessRoleIds());
        return allowedRoleIds.contains(userRoleId);
    }
    
    @Override
    public Page<Paper> getPapersPageWithAccess(int pageNum, int pageSize, Long userRoleId) {
        Page<Paper> page = new Page<>(pageNum, pageSize);
        
        // 构建查询条件，只返回用户有权限访问的试卷
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        
        // 查询所有试卷
        Page<Paper> allPapersPage = paperMapper.selectPage(page, null);
        
        // 过滤出用户有权限访问的试卷
        List<Paper> filteredPapers = new ArrayList<>();
        for (Paper paper : allPapersPage.getRecords()) {
            if (hasAccessToPaper(paper.getId(), userRoleId)) {
                filteredPapers.add(paper);
            }
        }
        
        // 创建新的分页结果
        Page<Paper> resultPage = new Page<>();
        resultPage.setCurrent(allPapersPage.getCurrent());
        resultPage.setSize(allPapersPage.getSize());
        resultPage.setTotal(countAccessiblePapers(userRoleId)); // 实际总数是用户可访问的试卷数
        resultPage.setRecords(filteredPapers);
        
        return resultPage;
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
    
    private long countAccessiblePapers(Long userRoleId) {
        List<Paper> allPapers = getAllPapers();
        return allPapers.stream()
                .filter(paper -> hasAccessToPaper(paper.getId(), userRoleId))
                .count();
    }
}