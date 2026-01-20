package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 权限实体类
 */
@Data
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("description")
    @NotBlank(message = "权限描述不能为空")
    @Size(max = 100, message = "权限描述长度不能超过100个字符")
    private String description;
}