package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 试卷实体类
 */
@Data
@TableName("paper")
public class Paper {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("paper_name")
    @NotBlank(message = "试卷名称不能为空")
    private String paperName;

    @TableField("paper_type")
    @NotNull(message = "试卷类型不能为空")
    @Min(value = 1, message = "试卷类型值不能小于1")
    private Integer paperType;

    @TableField("access_role_ids")
    @NotBlank(message = "访问角色ID列表不能为空")
    private String accessRoleIds; // 存储JSON格式的角色ID数组
}