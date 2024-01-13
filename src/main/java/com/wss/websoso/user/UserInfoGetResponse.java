package com.wss.websoso.user;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.userNovel.UserAvatarsGetResponse;
import java.util.List;

public record UserInfoGetResponse(
        Long representativeAvatarId,
        String representativeAvatarGenreBadge,
        String representativeAvatarTag,
        String representativeAvatarLineContent,
        String representativeAvatarImg,
        String userNickName,
        long userNovelCount,
        long memoCount,
        List<UserAvatarsGetResponse> userAvatarList
) {
    public static UserInfoGetResponse of(User user, Avatar avatar, long userNovelCount,
                                         AvatarLine avatarLine, long memoCount,
                                         List<UserAvatarsGetResponse> avatars) {
        return new UserInfoGetResponse(
                user.getUserRepAvatarId(),
                avatar.getAvatarGenreBadgeImg(),
                avatar.getAvatarTag(),
                avatarLine.getAvatarLineContent(),
                avatar.getAvatarAcquiredImg(),
                user.getUserNickname(),
                userNovelCount,
                memoCount,
                avatars
        );
    }
}
