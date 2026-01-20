package com.txwl.txwlplatform.service;

import com.txwl.txwlplatform.model.entity.Question;

import java.util.List;
import java.util.Map;

public interface IAiService {
    /**
     * 生成AI报告
     * @param answers 用户答案
     * @return AI生成的分析报告
     */
    String generateReport(Map<Long, Object> answers);

    /**
     * 基于题目和答案生成AI报告
     * @param questionAnswerPairs 题目和答案的配对
     * @return AI生成的分析报告
     */
    String generateReportWithQuestions(Map<Question, Object> questionAnswerPairs);
}