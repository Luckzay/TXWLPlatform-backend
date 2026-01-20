package com.txwl.txwlplatform.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户查询DTO（用于根据用户名或手机号查询）
 */
public class UserQueryDto {
    private String username;
    private String phone;

    // 只需要getter/setter，不需要验证注解，因为只需要其中一个
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}