package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("score_rank")
public class ScoreRank {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("exam_id")
    private Long examId;

    @TableField("subject_type")
    @NotBlank(message = "科类不能为空")
    private String subjectType;

    @TableField("score")
    @NotNull(message = "分数不能为空")
    private Integer score;

    @TableField("rank")
    @NotNull(message = "位次不能为空")
    private Integer rank;

    @TableField("count")
    @NotNull(message = "同分人数不能为空")
    private Integer count;
}

/**
 * 考试一分一段
 * CREATE TABLE score_rank (
 *                             id          BIGINT AUTO_INCREMENT PRIMARY KEY,
 *                             exam_id     BIGINT       NOT NULL COMMENT '关联考试表ID',
 *                             subject_type VARCHAR(20) NOT NULL COMMENT '科类（物理类、历史类）',
 *                             score       INT          NOT NULL COMMENT '分数',
 *                             rank        BIGINT       NOT NULL COMMENT '位次（累计人数）',
 *                             count       INT          DEFAULT 1 COMMENT '同分人数',
 *                             CONSTRAINT uk_exam_subject_score UNIQUE (exam_id, subject_type, score),
 *                             INDEX idx_exam_subject (exam_id, subject_type),
 *                             INDEX idx_score (score),
 *                             CONSTRAINT fk_rank_exam FOREIGN KEY (exam_id) REFERENCES exam(id) ON DELETE CASCADE
 * ) ENGINE=InnoDB COMMENT='一分一段表：考试-科类-分数-位次对应关系';
 */
