package com.txwl.txwlplatform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Paper;

import java.util.List;

public interface IPaperService {
    Page<Paper> getPapersPage(int pageNum, int pageSize);
    List<Paper> getAllPapers();
    Paper getPaperById(Long id);
    void createPaper(Paper paper);
    void updatePaper(Paper paper);
    void deletePaper(Long id);
    
    /**
     * 检查用户是否有权限访问指定试卷
     * @param paperId 试卷ID
     * @param userRoleId 用户角色ID
     * @return 是否有权限
     */
    boolean hasAccessToPaper(Long paperId, Long userRoleId);
    
    /**
     * 分页获取用户有权限访问的试卷
     */
    Page<Paper> getPapersPageWithAccess(int pageNum, int pageSize, Long userRoleId);
}