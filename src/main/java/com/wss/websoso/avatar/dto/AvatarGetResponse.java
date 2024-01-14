package com.wss.websoso.avatar.dto;

import com.wss.websoso.avatar.Avatar;

public record AvatarGetResponse(
        String avatarTag,
        String avatarGenreBadgeImg,
        String avatarMent,
        String avatarCondition
) {
    public static AvatarGetResponse of(Avatar avatar, String avatarMent, String avatarCondition) {
        return new AvatarGetResponse(
                avatar.getAvatarTag(),
                avatar.getAvatarGenreBadgeImg(),
                avatarMent,
                avatarCondition
        );
    }
}