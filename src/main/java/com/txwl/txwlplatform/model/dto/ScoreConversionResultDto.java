package com.txwl.txwlplatform.model.dto;

import lombok.Data;

@Data
public class ScoreConversionResultDto {
    private Integer year;           // 高考年份
    private String examName;        // 高考名称
    private Integer convertedScore; // 转换后的分数
    private String description;     // 描述信息（如">689分，<12名"）
    
    public ScoreConversionResultDto(Integer year, String examName, Integer convertedScore, String description) {
        this.year = year;
        this.examName = examName;
        this.convertedScore = convertedScore;
        this.description = description;
    }
}