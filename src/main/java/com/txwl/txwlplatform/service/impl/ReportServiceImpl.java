package com.txwl.txwlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txwl.txwlplatform.mapper.ReportMapper;
import com.txwl.txwlplatform.model.entity.Report;
import com.txwl.txwlplatform.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public Report createReport(Long userId, Long paperId, String reportUrl) {
        Report report = new Report();
        report.setUserId(userId);
        report.setPaperId(paperId);
        report.setUrl(reportUrl);
        report.setCreateTime(LocalDateTime.now());

        reportMapper.insert(report);
        return report;
    }

    @Override
    public List<Report> getReportsByUserId(Long userId) {
        QueryWrapper<Report> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return reportMapper.selectList(wrapper);
    }

    @Override
    public List<Report> getReportsByPaperId(Long paperId) {
        QueryWrapper<Report> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        return reportMapper.selectList(wrapper);
    }

    @Override
    public Report getReportById(Long reportId) {
        return reportMapper.selectById(reportId);
    }
}