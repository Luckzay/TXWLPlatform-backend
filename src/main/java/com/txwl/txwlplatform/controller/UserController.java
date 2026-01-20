package com.txwl.txwlplatform.controller;

import com.txwl.txwlplatform.model.dto.UsernameOrPhoneDto;
import com.txwl.txwlplatform.model.dto.UserRegisterDto;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 根据用户名或手机号查询用户
     */
    @PostMapping("/find-by-username-or-phone")
    public ResponseEntity<User> findByUsernameOrPhone(@RequestBody @Valid UsernameOrPhoneDto dto) {
        // 验证至少有一个字段不为空（在dto类上使用了@AtLeastOneField注解）
        User user = userService.findByUsernameOrPhone(dto).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegisterDto userDto) {
        // 实际的注册逻辑将在此处实现
        // 这里只是示例，验证所有必需字段
        return ResponseEntity.ok("用户注册成功");
    }

    /**
     * 检查用户名是否存在
     */
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    /**
     * 检查手机号是否存在
     */
    @GetMapping("/exists/phone/{phone}")
    public ResponseEntity<Boolean> existsByPhone(@PathVariable String phone) {
        boolean exists = userService.existsByPhone(phone);
        return ResponseEntity.ok(exists);
    }
}