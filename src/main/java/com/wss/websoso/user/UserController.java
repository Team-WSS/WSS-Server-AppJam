package com.wss.websoso.user;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(Principal principal, @RequestBody UserNicknameUpdateRequest userNicknameUpdateRequest) {
        try {
            Long userId = Long.valueOf(principal.getName());
            String newUserNickname = userNicknameUpdateRequest.userNickname();
            userService.updateNickname(userId, newUserNickname);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}