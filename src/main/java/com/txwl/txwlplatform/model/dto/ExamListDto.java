package com.txwl.txwlplatform.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExamListDto {
    private Long id;
    private Integer examYear;
    private String examName;
    private String displayName; // 显示名称，格式如："2025一模"
    
    public ExamListDto(Long id, Integer examYear, String examName) {
        this.id = id;
        this.examYear = examYear;
        this.examName = examName;
        this.displayName = examYear + examName;
    }
}