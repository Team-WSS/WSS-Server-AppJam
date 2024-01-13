package com.wss.websoso.user;

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
    public ResponseEntity<UserLoginRequest> login(@RequestParam String userNickname) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(userNickname));
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