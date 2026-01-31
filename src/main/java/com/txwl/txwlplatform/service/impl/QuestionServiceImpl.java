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
        // 优化：一次性获取所有题目和对应的所有答案，避免N+1查询问题
        List<Question> questions = getQuestionsByPaperId(paperId);
        
        if (questions.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 获取所有题目ID
        Set<Long> questionIds = questions.stream()
                .map(Question::getId)
                .collect(java.util.stream.Collectors.toSet());
        
        // 一次性查询所有题目对应的答案
        QueryWrapper<Answer> answerWrapper = new QueryWrapper<>();
        answerWrapper.in("question_id", questionIds);
        List<Answer> allAnswers = answerMapper.selectList(answerWrapper);
        
        // 将答案按照题目ID分组
        Map<Long, List<Answer>> answersByQuestionId = allAnswers.stream()
                .collect(java.util.stream.Collectors.groupingBy(Answer::getQuestionId));
        
        // 构建结果
        List<Map<String, Object>> result = new ArrayList<>();
        for (Question question : questions) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", question);
            
            // 从预加载的答案集合中获取对应题目的答案
            List<Answer> answers = answersByQuestionId.getOrDefault(question.getId(), new ArrayList<>());
            questionData.put("answers", answers);
            
            result.add(questionData);
        }

        return result;
    }

    @Override
    public Page<Question> getQuestionsByPaperIdPaginated(Long paperId, int pageNum, int pageSize) {
        Page<Question> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        return questionMapper.selectPage(page, wrapper);
    }

    @Override
    public Page<Map<String, Object>> getQuestionsWithAnswersByPaperIdPaginated(Long paperId, int pageNum, int pageSize) {
        // 先分页查询题目
        Page<Question> questionsPage = getQuestionsByPaperIdPaginated(paperId, pageNum, pageSize);
        
        // 获取当前页的题目列表
        List<Question> questions = questionsPage.getRecords();
        
        if (questions.isEmpty()) {
            Page<Map<String, Object>> resultPage = new Page<>();
            resultPage.setCurrent(questionsPage.getCurrent());
            resultPage.setSize(questionsPage.getSize());
            resultPage.setTotal(questionsPage.getTotal());
            resultPage.setRecords(new ArrayList<>());
            return resultPage;
        }
        
        // 获取当前页所有题目的ID
        Set<Long> questionIds = questions.stream()
                .map(Question::getId)
                .collect(java.util.stream.Collectors.toSet());
        
        // 一次性查询所有当前页题目对应的答案
        QueryWrapper<Answer> answerWrapper = new QueryWrapper<>();
        answerWrapper.in("question_id", questionIds);
        List<Answer> allAnswers = answerMapper.selectList(answerWrapper);
        
        // 将答案按照题目ID分组
        Map<Long, List<Answer>> answersByQuestionId = allAnswers.stream()
                .collect(java.util.stream.Collectors.groupingBy(Answer::getQuestionId));
        
        // 构建当前页的结果
        List<Map<String, Object>> records = new ArrayList<>();
        for (Question question : questions) {
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("question", question);
            
            // 从预加载的答案集合中获取对应题目的答案
            List<Answer> answers = answersByQuestionId.getOrDefault(question.getId(), new ArrayList<>());
            questionData.put("answers", answers);
            
            records.add(questionData);
        }
        
        // 创建结果分页对象
        Page<Map<String, Object>> resultPage = new Page<>();
        resultPage.setCurrent(questionsPage.getCurrent());
        resultPage.setSize(questionsPage.getSize());
        resultPage.setTotal(questionsPage.getTotal());
        resultPage.setRecords(records);
        
        return resultPage;
    }

    @Override
    public int getQuestionCountByPaperId(Long paperId) {
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        return Math.toIntExact(questionMapper.selectCount(wrapper));
    }

    @Override
    public Question createQuestion(Question question) {
        questionMapper.insert(question);
        return question;
    }

    @Override
    public Question updateQuestion(Question question) {
        questionMapper.updateById(question);
        return question;
    }

    @Override
    public void deleteQuestion(Long questionId) {
        // 删除题目前先删除相关答案
        QueryWrapper<Answer> answerWrapper = new QueryWrapper<>();
        answerWrapper.eq("question_id", questionId);
        answerMapper.delete(answerWrapper);
        
        // 再删除题目
        questionMapper.deleteById(questionId);
    }

    @Override
    public Answer createAnswer(Answer answer) {
        answerMapper.insert(answer);
        return answer;
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        answerMapper.updateById(answer);
        return answer;
    }

    @Override
    public void deleteAnswer(Long answerId) {
        answerMapper.deleteById(answerId);
    }
}