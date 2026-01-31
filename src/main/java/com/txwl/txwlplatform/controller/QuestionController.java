package com.txwl.txwlplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Answer;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 分页获取题目
     */
    @GetMapping("/page")
    public ResponseEntity<Page<Question>> getQuestionsPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        Page<Question> questionsPage = questionService.getQuestionsPage(pageNum, pageSize);
        return ResponseEntity.ok(questionsPage);
    }

    /**
     * 获取题目及其选项
     */
    @GetMapping("/{questionId}/with-answers")
    public ResponseEntity<Map<String, Object>> getQuestionWithAnswers(@PathVariable Long questionId) {
        Question question = questionService.getQuestionWithAnswers(questionId);
        if (question == null) {
            return ResponseEntity.notFound().build();
        }

        List<Answer> answers = questionService.getAnswersByQuestionId(questionId);

        Map<String, Object> response = new HashMap<>();
        response.put("question", question);
        response.put("answers", answers);

        return ResponseEntity.ok(response);
    }

    /**
     * 根据试卷ID获取题目
     */
    @GetMapping("/paper/{paperId}")
    @PreAuthorize("@paperPermissionEvaluator.hasPaperAccess(authentication, #paperId)")
    public ResponseEntity<List<Question>> getQuestionsByPaperId(@PathVariable Long paperId) {
        List<Question> questions = questionService.getQuestionsByPaperId(paperId);
        return ResponseEntity.ok(questions);
    }
}