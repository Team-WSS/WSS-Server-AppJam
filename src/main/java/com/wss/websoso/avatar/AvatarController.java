package com.wss.websoso.avatar;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;

    @PatchMapping("/rep-avatar")
    public ResponseEntity<Void> updateUserRepAvatar(@RequestBody UserRepAvatarUpdateRequest userRepAvatarUpdateRequest, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        Long newRepAvatarId = userRepAvatarUpdateRequest.avatarId();
        avatarService.updateUserRepAvatar(userId, newRepAvatarId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}