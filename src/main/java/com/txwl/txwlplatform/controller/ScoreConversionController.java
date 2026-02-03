package com.txwl.txwlplatform.controller;

import com.txwl.txwlplatform.model.dto.ExamListDto;
import com.txwl.txwlplatform.model.dto.ScoreConversionRequestDto;
import com.txwl.txwlplatform.model.dto.ScoreConversionResultDto;
import com.txwl.txwlplatform.service.IScoreConversionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/score-conversion")
@RequiredArgsConstructor
public class ScoreConversionController {
    
    private final IScoreConversionService scoreConversionService;
    
    @GetMapping("/exams")
    public ResponseEntity<List<ExamListDto>> getExamList() {
        try {
            List<ExamListDto> examList = scoreConversionService.getExamList();
            log.info("成功获取考试列表，共{}条记录", examList.size());
            return ResponseEntity.ok(examList);
        } catch (Exception e) {
            log.error("获取考试列表失败", e);
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/convert")
    public ResponseEntity<List<ScoreConversionResultDto>> convertScore(
            @Valid @RequestBody ScoreConversionRequestDto request) {
        try {
            log.info("收到分数转换请求: examId={}, subjectType={}, score={}", 
                    request.getExamId(), request.getSubjectType(), request.getScore());
            
            List<ScoreConversionResultDto> results = scoreConversionService.convertScore(request);
            
            if (results.isEmpty()) {
                log.warn("分数转换结果为空");
                return ResponseEntity.noContent().build();
            }
            
            log.info("分数转换成功，返回{}条结果", results.size());
            return ResponseEntity.ok(results);
            
        } catch (RuntimeException e) {
            log.error("分数转换失败: {}", e.getMessage());
            return ResponseEntity.badRequest().body(List.of(
                new ScoreConversionResultDto(null, null, null, e.getMessage())
            ));
        } catch (Exception e) {
            log.error("分数转换发生未知错误", e);
            return ResponseEntity.status(500).build();
        }
    }
}