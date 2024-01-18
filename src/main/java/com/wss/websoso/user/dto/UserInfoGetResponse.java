package com.wss.websoso.user.dto;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.user.User;
import com.wss.websoso.userNovel.dto.UserAvatarsGetResponse;
import java.util.List;

public record UserInfoGetResponse(
        Long representativeAvatarId,
        String representativeAvatarGenreBadge,
        String representativeAvatarTag,
        String representativeAvatarLineContent,
        String representativeAvatarImg,
        String userNickname,
        long userNovelCount,
        long memoCount,
        List<UserAvatarsGetResponse> userAvatars
) {
    public static UserInfoGetResponse of(User user, Avatar avatar, long userNovelCount,
                                         AvatarLine avatarLine, long memoCount,
                                         List<UserAvatarsGetResponse> avatars) {
        String userNickname = user.getUserNickname();
        String customUserLine = avatarLine.getAvatarLineContent().replaceAll("%s", userNickname);

        return new UserInfoGetResponse(
                user.getUserRepAvatarId(),
                avatar.getAvatarGenreBadgeImg(),
                avatar.getAvatarTag(),
                customUserLine,
                avatar.getAvatarAcquiredImg(),
                user.getUserNickname(),
                userNovelCount,
                memoCount,
                avatars
        );
    }
}
