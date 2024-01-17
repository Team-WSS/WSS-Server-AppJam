package com.wss.websoso.user;

import com.wss.websoso.user.dto.UserInfoGetResponse;
import com.wss.websoso.user.dto.UserLoginRequest;
import com.wss.websoso.user.dto.UserNicknameUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "유저 API", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "유저의 닉네임을 받아서 로그인 처리를 한다.")
    @Parameter(name = "userNickname", description = "유저의 닉네임", required = true)
    @PostMapping("/login")
    public ResponseEntity<UserLoginRequest> login(@RequestParam String userNickname) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(userNickname));
    }

    @Operation(summary = "닉네임 변경", description = "새로운 닉네임을 받아서 유저의 닉네임을 변경한다.")
    @PatchMapping("/nickname")
    public ResponseEntity<?> updateNickname(Principal principal,
                                            @RequestBody UserNicknameUpdateRequest userNicknameUpdateRequest) {
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

    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회한다.")
    @GetMapping("/info")
    public ResponseEntity<UserInfoGetResponse> getUserInfo(Principal principal) {
        Long userId = Long.valueOf(principal.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserInfo(userId));
    }
}