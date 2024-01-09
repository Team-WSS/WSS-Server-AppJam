package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.memo.Memo;
import com.wss.websoso.platform.Platform;
import java.util.List;

public record UserNovelInfosResponse(
        List<UserNovelMemoResponse> memoList,
        float userNovelRating,
        ReadStatus userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate,
        String userNovelDescription,
        String userNovelGenre,
        List<UserNovelPlatformsResponse> platformList
) {

    public static UserNovelInfosResponse of(List<Memo> memos, UserNovel userNovel, List<Platform> platforms) {
        List<UserNovelMemoResponse> memoList = memos.stream()
                .map(memo -> new UserNovelMemoResponse(
                        memo.getMemoId(),
                        memo.getMemoContent(),
                        memo.getCreatedDate()
                )).toList();

        List<UserNovelPlatformsResponse> platformList = platforms.stream()
                .map(platform -> new UserNovelPlatformsResponse(
                        platform.getPlatformName(),
                        platform.getPlatformUrl()
                )).toList();

        return new UserNovelInfosResponse(
                memoList,
                userNovel.getUserNovelRating(),
                userNovel.getUserNovelReadStatus(),
                userNovel.getUserNovelReadStartDate(),
                userNovel.getUserNovelReadEndDate(),
                userNovel.getUserNovelDescription(),
                userNovel.getUserNovelGenre(),
                platformList
        );
    }
}