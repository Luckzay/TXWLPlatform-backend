package com.txwl.txwlplatform.service;

import com.txwl.txwlplatform.model.entity.Role;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role getRoleByName(String roleName);

    @NotNull(message = "角色ID不能为空") Long getRoleIdByName(String identity);
}