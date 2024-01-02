package com.wss.websoso.user;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userNickname) {
        try {
            return ResponseEntity.ok().body(userService.login(userNickname)); // 토큰 body에 담아서 보냄
        } catch (AuthException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}