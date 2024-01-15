package com.wss.websoso.userNovel.dto;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.genreBadge.GenreBadge;
import com.wss.websoso.memo.Memo;
import com.wss.websoso.platform.Platform;
import com.wss.websoso.userNovel.UserNovel;

import java.util.List;

public record UserNovelMemoAndInfoGetResponse(
        List<UserNovelMemosGetResponse> memos,
        float userNovelRating,
        String userNovelTitle,
        String userNovelImg,
        String userNovelAuthor,
        ReadStatus userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate,
        String userNovelDescription,
        String userNovelGenre,
        String userNovelGenreBadgeImg,
        List<UserNovelPlatformsGetResponse> platforms
) {

    public static UserNovelMemoAndInfoGetResponse of(List<Memo> memos, UserNovel userNovel,
                                                     List<Platform> platforms, GenreBadge genreBadge) {
        List<UserNovelMemosGetResponse> memoList = memos.stream()
                .map(memo -> new UserNovelMemosGetResponse(
                        memo.getMemoId(),
                        memo.getMemoContent(),
                        memo.getCreatedDate()
                )).toList();

        List<UserNovelPlatformsGetResponse> platformList = platforms.stream()
                .map(platform -> new UserNovelPlatformsGetResponse(
                        platform.getPlatformName(),
                        platform.getPlatformUrl()
                )).toList();

        return new UserNovelMemoAndInfoGetResponse(
                memoList,
                userNovel.getUserNovelRating(),
                userNovel.getUserNovelTitle(),
                userNovel.getUserNovelImg(),
                userNovel.getUserNovelAuthor(),
                userNovel.getUserNovelReadStatus(),
                userNovel.getUserNovelReadStartDate(),
                userNovel.getUserNovelReadEndDate(),
                userNovel.getUserNovelDescription(),
                userNovel.getUserNovelGenre(),
                genreBadge.getGenreBadgeImg(),
                platformList
        );
    }
}