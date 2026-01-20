package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 报告实体类
 */
@Data
@TableName("report")
public class Report {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @TableField("paper_id")
    @NotNull(message = "试卷ID不能为空")
    private Long paperId;

    @NotBlank(message = "报告URL不能为空")
    private String url;

    @TableField("create_time")
    private LocalDateTime createTime;
}