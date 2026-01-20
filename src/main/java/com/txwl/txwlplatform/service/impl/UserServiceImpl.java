package com.txwl.txwlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txwl.txwlplatform.model.dto.UsernameOrPhoneDto;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.mapper.UserMapper;
import com.txwl.txwlplatform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsernameOrPhone(UsernameOrPhoneDto dto) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        
        // 如果提供了用户名，添加用户名条件
        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            wrapper.eq("username", dto.getUsername());
        }
        
        // 如果提供了手机号，添加手机号条件
        if (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) {
            wrapper.eq("phone", dto.getPhone());
        }
        
        // 如果两个字段都没有提供，返回空结果
        if (wrapper.isEmptyOfWhere()) {
            return Optional.empty();
        }
        
        User user = userMapper.selectOne(wrapper);
        return Optional.ofNullable(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public void createUser(User user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public User getUserByIdentifier(String identifier) {
        // 首先尝试按用户名查找
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", identifier);
        User user = userMapper.selectOne(wrapper);
        
        // 如果按用户名没找到，尝试按手机号查找
        if (user == null) {
            wrapper = new QueryWrapper<>();
            wrapper.eq("phone", identifier);
            user = userMapper.selectOne(wrapper);
        }
        
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
}