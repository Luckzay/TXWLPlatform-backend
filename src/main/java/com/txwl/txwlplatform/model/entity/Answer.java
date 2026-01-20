package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 答案实体类
 */
@Data
@TableName("answer")
public class Answer {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("question_id")
    @NotNull(message = "题目ID不能为空")
    private Long questionId;

    @NotBlank(message = "答案内容不能为空")
    private String content;

    @NotBlank(message = "正确程度不能为空")
    private String correctness; // CORRECT, WRONG, OPEN
}