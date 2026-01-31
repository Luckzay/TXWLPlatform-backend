package com.txwl.txwlplatform.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.txwl.txwlplatform.model.dto.UserLoginDto;
import com.txwl.txwlplatform.model.dto.UserRegisterDto;
import com.txwl.txwlplatform.model.entity.User;
import com.txwl.txwlplatform.mapper.UserMapper;
import com.txwl.txwlplatform.security.util.JwtUtil;
import com.txwl.txwlplatform.service.IRoleService;
import com.txwl.txwlplatform.service.IUserService;
import com.txwl.txwlplatform.service.impl.RoleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto loginDto) {
        try {
            // 使用手机号进行身份验证
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getIdentifier(), loginDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 查询完整的用户信息用于生成token和返回给前端
        User user = userService.getUserByIdentifier(loginDto.getIdentifier());
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }
        
        // 生成JWT token，包含用户名和角色ID
        final String jwt = jwtUtil.generateToken(user.getUsername(), user.getRoleId());
        
        // 创建包含用户信息的响应对象
        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setUserUid(user.getUid());
        response.setUsername(user.getUsername());
        response.setRealname(user.getRealname());
        response.setPhone(user.getPhone());
        response.setRoleId(user.getRoleId());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDto registerDto) {
        // 检查手机号是否已存在
        if (userService.existsByPhone(registerDto.getPhone())) {
            return ResponseEntity.badRequest().body("Phone number already exists");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(registerDto.getPhone()); // 使用手机号作为用户名
        user.setPhone(registerDto.getPhone());
        user.setPassword(registerDto.getPassword()); // 不在这里加密，让服务层处理
        user.setRealname(registerDto.getRealname());
        user.setRoleId(roleService.getRoleIdByName(registerDto.getIdentity()));

        // 保存到数据库
        userService.createUser(user);

        return ResponseEntity.ok("Registration successful");
    }

    // 登录响应类，包含用户基本信息
    public static class LoginResponse {
        private String token;
        private Long userUid;
        private String username;
        private String realname;
        private String phone;
        private Long roleId;

        public LoginResponse() {}

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Long getUserUid() {
            return userUid;
        }

        public void setUserUid(Long userUid) {
            this.userUid = userUid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }
    }

    // JWT响应类（保留原有类以兼容）
    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}