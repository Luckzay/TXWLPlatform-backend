package com.txwl.txwlplatform.controller;

import com.txwl.txwlplatform.model.dto.QuestionnaireSubmitDto;
import com.txwl.txwlplatform.service.IQuestionnaireSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireSubmissionController {

    @Autowired
    private IQuestionnaireSubmissionService questionnaireSubmissionService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitQuestionnaire(@RequestBody QuestionnaireSubmitDto dto) {
        try {
            String reportUrl = questionnaireSubmissionService.submitQuestionnaire(
                dto.getUserId(), 
                dto.getPaperId(), 
                dto.getAnswers()
            );
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "问卷提交成功",
                "reportUrl", reportUrl
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "问卷提交失败: " + e.getMessage()
            ));
        }
    }
}