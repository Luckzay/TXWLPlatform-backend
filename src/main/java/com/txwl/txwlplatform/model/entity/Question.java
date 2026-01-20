package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 题目实体类
 */
@Data
@TableName("question")
public class Question {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("question_text")
    @NotBlank(message = "题干内容不能为空")
    private String questionText;

    @TableField("q_type")
    @NotBlank(message = "题型不能为空")
    private String qType; // SINGLE, MULTI, BLANK, TEXT, SCORE, FILE
    
    @TableField("paper_id")
    private Long paperId; // 关联试卷ID
}