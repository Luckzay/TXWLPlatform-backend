package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("exam")
public class Exam {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("exam_year")
    @NotNull(message = "考试年份不能为空")
    private Integer examYear;

    @TableField("exam_name")
    @NotBlank(message = "考试名称不能为空")
    private String examName;

    @TableField("description")
    private String description;

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;
    
    // 获取显示名称
    public String getDisplayName() {
        return examYear + examName;
    }

}

/**
 * 考试实体类
 * CREATE TABLE exam (
 *                       id          BIGINT AUTO_INCREMENT PRIMARY KEY,
 *                       exam_year   INT          NOT NULL COMMENT '考试年份，如：2024',
 *                       exam_name   VARCHAR(50)  NOT NULL COMMENT '考试名称（如：高考、一模、二模）',
 *                       description VARCHAR(200) COMMENT '考试描述',
 *                       create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
 *                       update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 *                       CONSTRAINT uk_exam_year_name UNIQUE (exam_year, exam_name)
 * ) ENGINE=InnoDB COMMENT='考试字典表';
 */