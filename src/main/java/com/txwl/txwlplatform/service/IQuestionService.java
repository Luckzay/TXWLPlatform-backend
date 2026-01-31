package com.txwl.txwlplatform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Answer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IQuestionService {
    Page<Question> getQuestionsPage(int pageNum, int pageSize);
    List<Question> getQuestionsByPaperId(Long paperId);
    List<Question> getQuestionsByIds(Set<Long> questionIds);
    List<Answer> getAnswersByQuestionId(Long questionId);
    Question getQuestionWithAnswers(Long questionId);
    List<Map<String, Object>> getQuestionsWithAnswersByPaperId(Long paperId);
    
    /**
     * 分页查询指定试卷的题目
     */
    Page<Question> getQuestionsByPaperIdPaginated(Long paperId, int pageNum, int pageSize);
    
    /**
     * 分页查询指定试卷的题目及答案
     */
    Page<Map<String, Object>> getQuestionsWithAnswersByPaperIdPaginated(Long paperId, int pageNum, int pageSize);
    
    /**
     * 根据试卷ID获取题目总数
     */
    int getQuestionCountByPaperId(Long paperId);
    
    /**
     * 创建题目
     */
    Question createQuestion(Question question);
    
    /**
     * 更新题目
     */
    Question updateQuestion(Question question);
    
    /**
     * 删除题目
     */
    void deleteQuestion(Long questionId);
    
    /**
     * 创建答案/选项
     */
    Answer createAnswer(Answer answer);
    
    /**
     * 更新答案/选项
     */
    Answer updateAnswer(Answer answer);
    
    /**
     * 删除答案/选项
     */
    void deleteAnswer(Long answerId);
}