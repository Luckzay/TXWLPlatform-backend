package com.txwl.txwlplatform.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScoreConversionRequestDto {
    @NotNull(message = "考试ID不能为空")
    private Long examId;
    
    @NotBlank(message = "科目类型不能为空")
    private String subjectType; // 物理类、历史类
    
    @NotNull(message = "考试分数不能为空")
    private Integer score;
}