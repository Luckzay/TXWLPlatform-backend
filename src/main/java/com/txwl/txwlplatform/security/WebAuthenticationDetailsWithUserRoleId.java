package com.txwl.txwlplatform.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

public class WebAuthenticationDetailsWithUserRoleId extends WebAuthenticationDetails {
    private final Long userRoleId;

    public WebAuthenticationDetailsWithUserRoleId(HttpServletRequest request, Long userRoleId) {
        super(request);
        this.userRoleId = userRoleId;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    @Override
    public String toString() {
        return super.toString() + ", userRoleId=" + userRoleId;
    }
}