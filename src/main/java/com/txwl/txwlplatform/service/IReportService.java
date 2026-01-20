package com.txwl.txwlplatform.service;

import com.txwl.txwlplatform.model.entity.Report;
import java.util.List;

public interface IReportService {
    /**
     * 创建报告
     */
    Report createReport(Long userId, Long paperId, String reportUrl);

    /**
     * 根据用户ID获取报告列表
     */
    List<Report> getReportsByUserId(Long userId);

    /**
     * 根据试卷ID获取报告列表
     */
    List<Report> getReportsByPaperId(Long paperId);

    /**
     * 获取报告详情
     */
    Report getReportById(Long reportId);
}