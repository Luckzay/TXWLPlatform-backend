package com.txwl.txwlplatform.model.dto;

import com.txwl.txwlplatform.validation.AtLeastOneField;
import jakarta.validation.constraints.Pattern;

/**
 * 用户名或手机号验证DTO
 */
@AtLeastOneField(fields = {"username", "phone"}, message = "用户名和手机号不能同时为空")
public class UsernameOrPhoneDto {
    private String username;
    
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    // 验证器将在Controller中检查至少有一个字段不为空
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