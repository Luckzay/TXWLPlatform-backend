package com.txwl.txwlplatform.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 角色权限关联实体类
 */
@Data
@TableName("role_permission")
public class RolePermission {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("role_id")
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    @TableField("permission_id")
    @NotNull(message = "权限ID不能为空")
    private Long permissionId;
}