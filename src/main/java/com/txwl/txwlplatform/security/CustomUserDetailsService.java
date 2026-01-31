package com.txwl.txwlplatform.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txwl.txwlplatform.mapper.RoleMapper;
import com.txwl.txwlplatform.mapper.UserMapper;
import com.txwl.txwlplatform.model.entity.Role;
import com.txwl.txwlplatform.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 首先尝试按用户名查找
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);

        // 如果用户名没找到，尝试按手机号查找
        if (user == null) {
            wrapper = new QueryWrapper<>();
            wrapper.eq("phone", username);
            user = userMapper.selectOne(wrapper);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // 获取用户角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        String roleName = "ROLE_" + roleMapper.selectById(user.getRoleId()).getRoleName();
        // 添加用户的角色信息
        authorities.add(new SimpleGrantedAuthority(roleName));

        // 返回包含用户角色ID的UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}