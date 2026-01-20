package com.txwl.txwlplatform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txwl.txwlplatform.model.entity.Question;
import com.txwl.txwlplatform.model.entity.Answer;
import com.txwl.txwlplatform.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> getQuestionsForAnsweringPaginated(
            @PathVariable Long paperId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        // 获取该试卷的所有题目及选项
        List<Map<String, Object>> allQuestionsWithAnswers = questionService.getQuestionsWithAnswersByPaperId(paperId);
        
        // 手动分页处理
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allQuestionsWithAnswers.size());
        
        List<Map<String, Object>> paginatedQuestions = allQuestionsWithAnswers.subList(startIndex, endIndex);
        
        Map<String, Object> response = new HashMap<>();
        response.put("paperId", paperId);
        response.put("totalQuestions", allQuestionsWithAnswers.size());
        response.put("currentPage", pageNum);
        response.put("totalPages", (int) Math.ceil((double) allQuestionsWithAnswers.size() / pageSize));
        response.put("questions", paginatedQuestions);
        
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