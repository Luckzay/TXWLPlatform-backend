package com.txwl.txwlplatform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Paper;
import java.util.List;

public interface IPaperService {
    /**
     * 分页获取试卷列表
     */
    Page<Paper> getPapersPage(int pageNum, int pageSize);

    /**
     * 获取所有试卷
     */
    List<Paper> getAllPapers();

    /**
     * 根据ID获取试卷详情
     */
    Paper getPaperById(Long id);

    /**
     * 创建试卷
     */
    void createPaper(Paper paper);

    /**
     * 更新试卷
     */
    void updatePaper(Paper paper);

    /**
     * 删除试卷
     */
    void deletePaper(Long id);
}