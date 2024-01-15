package com.wss.websoso.userNovel.dto;

public record UserAvatarsGetResponse(
        Long avatarId,
        String avatarImg,
        boolean hasAvatar
) {
}
