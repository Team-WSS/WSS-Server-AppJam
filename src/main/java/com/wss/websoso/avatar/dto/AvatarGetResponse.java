package com.wss.websoso.avatar.dto;

import com.wss.websoso.avatar.Avatar;

public record AvatarGetResponse(
        Long avatarId,
        String avatarImg,
        String avatarTag,
        String avatarGenreBadgeImg,
        String avatarMent,
        String avatarCondition,
        boolean hasAvatar
) {
    public static AvatarGetResponse of(Avatar avatar, boolean hasAvatar) {
        return new AvatarGetResponse(
                avatar.getAvatarId(),
                hasAvatar ? "" : avatar.getAvatarUnacquiredImg(),
                avatar.getAvatarTag(),
                avatar.getAvatarGenreBadgeImg(),
                hasAvatar ? avatar.getAvatarAcquiredMent() : avatar.getAvatarUnacquiredMent(),
                hasAvatar ? avatar.getAvatarAcquiredCondition() : avatar.getAvatarUnacquiredCondition(),
                hasAvatar
        );
    }
}