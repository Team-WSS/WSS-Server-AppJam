package com.wss.websoso.avatar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wss.websoso.avatar.Avatar;

public record AvatarGetResponse(
        Long avatarId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
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
                hasAvatar ? null : avatar.getAvatarUnacquiredImg(),
                avatar.getAvatarTag(),
                avatar.getAvatarGenreBadgeImg(),
                hasAvatar ? avatar.getAvatarAcquiredMent() : avatar.getAvatarUnacquiredMent(),
                hasAvatar ? avatar.getAvatarAcquiredCondition() : avatar.getAvatarUnacquiredCondition(),
                hasAvatar
        );
    }
}