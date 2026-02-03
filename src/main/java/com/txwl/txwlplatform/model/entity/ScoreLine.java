package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("score_line")
public class ScoreLine {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("exam_id")
    @NotNull(message = "考试ID不能为空")
    private Long examId;

    @TableField("subject_type")
    @NotBlank(message = "科类不能为空")
    private String subjectType;

    @TableField("qingbei_score")
    private Integer qingbeiScore;

    @TableField("score_985")
    private Integer score985;

    @TableField("score_211")
    private Integer score211;

    @TableField("tekong_score")
    @NotNull(message = "特控线分数不能为空")
    private Integer tekongScore;

    @TableField("benke_score")
    @NotNull(message = "本科线分数不能为空")
    private Integer benkeScore;

    @TableField("update_time")
    private String updateTime;
}

/**
 * 考试分数线实体类
 * CREATE TABLE score_line (
 *                             id              BIGINT AUTO_INCREMENT PRIMARY KEY,
 *                             exam_id         BIGINT       NOT NULL COMMENT '关联考试表ID',
 *                             subject_type    VARCHAR(20)  NOT NULL COMMENT '科类（物理类、历史类）',
 *                             qingbei_score   INT          COMMENT '清北线分数（可选）',
 *                             score_985       INT          COMMENT '985线分数（可选）',
 *                             score_211       INT          COMMENT '211线分数（可选）',
 *                             tekong_score    INT          NOT NULL COMMENT '特控线（一本线）分数',
 *                             benke_score     INT          NOT NULL COMMENT '本科线分数',
 *                             update_time     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 *                             CONSTRAINT uk_exam_subject_line UNIQUE (exam_id, subject_type),
 *                             INDEX idx_exam_id (exam_id),
 *                             CONSTRAINT fk_line_exam FOREIGN KEY (exam_id) REFERENCES exam(id) ON DELETE CASCADE
 * ) ENGINE=InnoDB COMMENT='分数线表：考试-科类-各批次线';
 */