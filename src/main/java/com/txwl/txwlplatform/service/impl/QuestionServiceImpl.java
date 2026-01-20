package com.txwl.txwlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txwl.txwlplatform.mapper.QuestionMapper;
import com.txwl.txwlplatform.mapper.AnswerMapper;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Answer;
import com.txwl.txwlplatform.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Page<Question> getQuestionsPage(int pageNum, int pageSize) {
        Page<Question> page = new Page<>(pageNum, pageSize);
        return questionMapper.selectPage(page, null);
    }

    @Override
    public List<Question> getQuestionsByPaperId(Long paperId) {
        // 根据试卷ID查询题目
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        return questionMapper.selectList(wrapper);
    }

    @Override
    public List<Question> getQuestionsByIds(Set<Long> questionIds) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.in("id", questionIds);
        return questionMapper.selectList(wrapper);
    }

    @Override
    public List<Answer> getAnswersByQuestionId(Long questionId) {
        QueryWrapper<Answer> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", questionId);
        return answerMapper.selectList(wrapper);
    }

    @Override
    public Question getQuestionWithAnswers(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if (question != null) {
            // 调用本类的其他方法是合理的，因为它们也属于同一Service层
            List<Answer> answers = getAnswersByQuestionId(questionId);
            // 这里可以将答案设置到题目对象中，如果需要的话
            // 由于Answer不在Question实体中，我们可以通过其他方式处理
        }
        return question;
    }

    @Override
    public List<Map<String, Object>> getQuestionsWithAnswersByPaperId(Long paperId) {
        List<Question> questions = getQuestionsByPaperId(paperId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Question question : questions) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", question);
            
            List<Answer> answers = getAnswersByQuestionId(question.getId());
            questionData.put("answers", answers);
            
            result.add(questionData);
        }

        return result;
    }
}