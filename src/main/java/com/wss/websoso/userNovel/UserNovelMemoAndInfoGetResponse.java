package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.memo.Memo;
import com.wss.websoso.platform.Platform;
import java.util.List;

public record UserNovelMemoAndInfoGetResponse(
        List<UserNovelMemosGetResponse> memoList,
        float userNovelRating,
        ReadStatus userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate,
        String userNovelDescription,
        String userNovelGenre,
        List<UserNovelPlatformsGetResponse> platformList
) {

    public static UserNovelMemoAndInfoGetResponse of(List<Memo> memos, UserNovel userNovel, List<Platform> platforms) {
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
                userNovel.getUserNovelReadStatus(),
                userNovel.getUserNovelReadStartDate(),
                userNovel.getUserNovelReadEndDate(),
                userNovel.getUserNovelDescription(),
                userNovel.getUserNovelGenre(),
                platformList
        );
    }
}