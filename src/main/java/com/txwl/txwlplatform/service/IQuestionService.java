package com.txwl.txwlplatform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Answer;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IQuestionService {
    /**
     * 分页获取题目
     */
    Page<Question> getQuestionsPage(int pageNum, int pageSize);

    /**
     * 根据试卷ID获取题目列表
     */
    List<Question> getQuestionsByPaperId(Long paperId);

    /**
     * 根据题目ID列表获取题目列表
     */
    List<Question> getQuestionsByIds(Set<Long> questionIds);

    /**
     * 获取题目的选项
     */
    List<Answer> getAnswersByQuestionId(Long questionId);
    
    /**
     * 根据题目ID获取完整信息（含选项）
     */
    Question getQuestionWithAnswers(Long questionId);

    /**
     * 获取试卷的完整题目和选项信息
     */
    List<Map<String, Object>> getQuestionsWithAnswersByPaperId(Long paperId);
}