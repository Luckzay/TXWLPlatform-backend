package com.txwl.txwlplatform.controller;

import com.txwl.txwlplatform.model.entity.Report;
import com.txwl.txwlplatform.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private IReportService reportService;

    /**
     * 创建报告
     */
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        // 这里应该在实际实现中调用AI生成报告的逻辑
        Report createdReport = reportService.createReport(
            report.getUserId(), 
            report.getPaperId(), 
            report.getUrl()
        );
        return ResponseEntity.ok(createdReport);
    }

    /**
     * 根据用户ID获取报告列表
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Report>> getReportsByUserId(@PathVariable Long userId) {
        List<Report> reports = reportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 根据试卷ID获取报告列表
     */
    @GetMapping("/paper/{paperId}")
    public ResponseEntity<List<Report>> getReportsByPaperId(@PathVariable Long paperId) {
        List<Report> reports = reportService.getReportsByPaperId(paperId);
        return ResponseEntity.ok(reports);
    }

    /**
     * 获取报告详情
     */
    @GetMapping("/{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable Long reportId) {
        Report report = reportService.getReportById(reportId);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
}