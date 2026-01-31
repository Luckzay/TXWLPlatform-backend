package com.txwl.txwlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txwl.txwlplatform.mapper.RoleMapper;
import com.txwl.txwlplatform.model.entity.Role;
import com.txwl.txwlplatform.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.selectList(null);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Role getRoleByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", roleName);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public Long getRoleIdByName(String identity) {
        return roleMapper.selectOne(new QueryWrapper<Role>().eq("role_name", identity)).getId();
    }
}