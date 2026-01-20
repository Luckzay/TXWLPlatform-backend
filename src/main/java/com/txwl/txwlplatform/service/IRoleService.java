package com.txwl.txwlplatform.service;

import com.txwl.txwlplatform.model.entity.Role;
import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
    Role getRoleByName(String roleName);
}