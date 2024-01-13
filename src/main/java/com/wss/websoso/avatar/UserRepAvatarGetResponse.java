package com.wss.websoso.avatar;

public record UserRepAvatarGetResponse(
        String avatarTag,
        String avatarLine,
        String userNickname
) {
    public static UserRepAvatarGetResponse of(Avatar avatar, String avatarLine, String userNickname) {
        return new UserRepAvatarGetResponse(
                avatar.getAvatarTag(),
                avatarLine,
                userNickname
        );
    }
}