package com.wss.websoso.user.dto;

public record UserLoginRequest(
        String Authorization
) {
    public static UserLoginRequest of(String token) {
        return new UserLoginRequest(token);
    }
}
