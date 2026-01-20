package com.txwl.txwlplatform.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录DTO
 */
public class UserLoginDto {
    @NotBlank(message = "用户名或手机号不能为空")
    private String identifier; // 用户名或手机号

    @NotBlank(message = "密码不能为空")
    private String password;

    // getters and setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}