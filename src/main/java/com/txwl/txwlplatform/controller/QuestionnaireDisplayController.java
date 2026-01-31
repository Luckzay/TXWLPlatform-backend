package com.txwl.txwlplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Answer;
import com.txwl.txwlplatform.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireDisplayController {

    @Autowired
    private IQuestionService questionService;

    /**
     * 获取问卷题目用于作答界面展示
     */
    @GetMapping("/paper/{paperId}/questions")
    @PreAuthorize("@paperPermissionEvaluator.hasPaperAccess(authentication, #paperId)")
    public ResponseEntity<Map<String, Object>> getQuestionsForAnswering(@PathVariable Long paperId) {
        // 获取该试卷的所有题目及选项
        List<Map<String, Object>> questionsWithAnswers = questionService.getQuestionsWithAnswersByPaperId(paperId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("paperId", paperId);
        response.put("totalQuestions", questionsWithAnswers.size());
        response.put("questions", questionsWithAnswers);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 分页获取问卷题目用于作答界面展示
     */
    @GetMapping("/paper/{paperId}/questions/page")
    @PreAuthorize("@paperPermissionEvaluator.hasPaperAccess(authentication, #paperId)")
    public ResponseEntity<Map<String, Object>> getQuestionsForAnsweringPaginated(
            @PathVariable Long paperId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        // 使用数据库分页查询题目及答案
        Page<Map<String, Object>> questionsPage = questionService.getQuestionsWithAnswersByPaperIdPaginated(paperId, pageNum, pageSize);
        
        Map<String, Object> response = new HashMap<>();
        response.put("paperId", paperId);
        response.put("totalQuestions", (int) questionsPage.getTotal());
        response.put("currentPage", (int) questionsPage.getCurrent());
        response.put("totalPages", (int) questionsPage.getPages());
        response.put("questions", questionsPage.getRecords());
        
        return ResponseEntity.ok(response);
    }

    /**
     * 获取单个题目的详细信息（包含选项）
     */
    @GetMapping("/question/{questionId}/detail")
    public ResponseEntity<Map<String, Object>> getQuestionDetail(@PathVariable Long questionId) {
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
}