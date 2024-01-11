package com.wss.websoso.user;

public record UserNicknameUpdateResponse(
        String userNickname,
        String Authorization
) {
    public static UserNicknameUpdateResponse of(String userNickname, String Authorization) {
        return new UserNicknameUpdateResponse(userNickname, Authorization);
    }
}
