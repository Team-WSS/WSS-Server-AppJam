package com.wss.websoso.avatar;

import com.wss.websoso.avatar.dto.AvatarGetResponse;
import com.wss.websoso.avatar.dto.UserRepAvatarGetResponse;
import com.wss.websoso.avatar.dto.UserRepAvatarUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "아바타 API", description = "아바타 관련 API")
@RestController
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @Operation(summary = "대표 아바타 조회", description = "유저의 대표 아바타 정보를 조회한다.")
    @GetMapping("/rep-avatar")
    public ResponseEntity<UserRepAvatarGetResponse> getRepAvatar(Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.getRepAvatar(userId));
    }

    @Operation(summary = "대표 캐릭터 변경", description = "유저의 대표 아바타를 변경한다.")
    @Parameter(name = "userRepAvatarUpdateRequest", description = "변경할 대표 아바타 ID", required = true)
    @PatchMapping("/rep-avatar")
    public ResponseEntity<Void> updateUserRepAvatar(@RequestBody UserRepAvatarUpdateRequest userRepAvatarUpdateRequest,
                                                    Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        Long newRepAvatarId = userRepAvatarUpdateRequest.avatarId();
        avatarService.updateUserRepAvatar(userId, newRepAvatarId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "아바타 단건 정보 조회", description = "아바타 단건 정보를 조회한다.")
    @Parameter(name = "avatarId", description = "조회할 아바타 ID", required = true)
    @GetMapping("/avatars/{avatarId}")
    public ResponseEntity<AvatarGetResponse> getAvatar(@PathVariable Long avatarId, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.getAvatar(userId, avatarId));
    }
}