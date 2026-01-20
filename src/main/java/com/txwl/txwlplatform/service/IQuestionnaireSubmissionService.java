package com.txwl.txwlplatform.service;

import java.util.Map;

public interface IQuestionnaireSubmissionService {
    /**
     * 提交问卷答案并生成报告
     */
    String submitQuestionnaire(Long userId, Long paperId, Map<Long, Object> answers);
}