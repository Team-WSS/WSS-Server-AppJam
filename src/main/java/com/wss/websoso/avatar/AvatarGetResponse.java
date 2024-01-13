package com.wss.websoso.avatar;

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