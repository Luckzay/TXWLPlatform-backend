package com.txwl.txwlplatform.service;

import com.txwl.txwlplatform.model.dto.UsernameOrPhoneDto;
import com.txwl.txwlplatform.model.entity.User;

import java.util.Optional;

public interface IUserService {
    /**
     * 根据用户名或手机号查找用户
     */
    Optional<User> findByUsernameOrPhone(UsernameOrPhoneDto dto);

    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查手机号是否存在
     */
    boolean existsByPhone(String phone);

    void createUser(User user);

    /**
     * 根据用户名或手机号获取用户
     */
    User getUserByIdentifier(String identifier);

    /**
     * 根据用户ID获取用户
     */
    User getUserById(Long id);
}