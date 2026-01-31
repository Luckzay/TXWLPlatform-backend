package com.txwl.txwlplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Paper;
import com.txwl.txwlplatform.security.WebAuthenticationDetailsWithUserRoleId;
import com.txwl.txwlplatform.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    @Autowired
    private IPaperService paperService;

    /**
     * 分页获取试卷列表
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Paper>> getPapersPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            Authentication authentication) {
        
        // 从认证信息中获取用户角色ID
        Long userRoleId = null;
        if (authentication != null) {
            var details = authentication.getDetails();
            if (details instanceof WebAuthenticationDetailsWithUserRoleId) {
                userRoleId = ((WebAuthenticationDetailsWithUserRoleId) details).getUserRoleId();
            }
        }
        
        Page<Paper> papersPage = paperService.getPapersPageWithAccess(pageNum, pageSize, userRoleId);
        return ResponseEntity.ok(papersPage);
    }

    /**
     * 获取所有试卷
     */
    @GetMapping
    public ResponseEntity<List<Paper>> getAllPapers() {
        List<Paper> papers = paperService.getAllPapers();
        return ResponseEntity.ok(papers);
    }

    /**
     * 根据ID获取试卷详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paper> getPaperById(@PathVariable Long id) {
        Paper paper = paperService.getPaperById(id);
        if (paper == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paper);
    }

    /**
     * 获取试卷基本信息列表（仅包含ID、名称、类型等基本信息）
     */
    @GetMapping("/basic-info")
    public ResponseEntity<List<Map<String, Object>>> getPapersBasicInfo() {
        List<Paper> papers = paperService.getAllPapers();
        List<Map<String, Object>> basicInfoList = papers.stream().map(this::convertToBasicInfo).toList();
        return ResponseEntity.ok(basicInfoList);
    }

    /**
     * 根据ID获取试卷基本信息（仅包含ID、名称、类型等基本信息）
     */
    @GetMapping("/{id}/basic-info")
    public ResponseEntity<Map<String, Object>> getPaperBasicInfo(@PathVariable Long id) {
        Paper paper = paperService.getPaperById(id);
        if (paper == null) {
            return ResponseEntity.notFound().build();
        }
        
        Map<String, Object> basicInfo = convertToBasicInfo(paper);
        return ResponseEntity.ok(basicInfo);
    }

    /**
     * 创建试卷
     */
    @PostMapping
    public ResponseEntity<Void> createPaper(@RequestBody Paper paper) {
        paperService.createPaper(paper);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新试卷
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePaper(@PathVariable Long id, @RequestBody Paper paper) {
        paper.setId(id);
        paperService.updatePaper(paper);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除试卷
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        paperService.deletePaper(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 将试卷转换为基本信息映射
     */
    private Map<String, Object> convertToBasicInfo(Paper paper) {
        Map<String, Object> basicInfo = new HashMap<>();
        basicInfo.put("id", paper.getId());
        basicInfo.put("paperName", paper.getPaperName());
        basicInfo.put("paperType", paper.getPaperType());
        return basicInfo;
    }
}