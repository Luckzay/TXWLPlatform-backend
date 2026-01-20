package com.txwl.txwlplatform.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.mapper.UserMapper;
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
        // 这里可以根据用户的角色信息添加权限
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}