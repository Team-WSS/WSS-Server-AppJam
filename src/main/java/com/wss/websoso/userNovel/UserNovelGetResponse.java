package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.platform.PlatformGetResponse;

import java.util.List;

public record UserNovelGetResponse(
        Long userNovelId,
        String userNovelTitle,
        String userNovelAuthor,
        String userNovelGenre,
        String userNovelImg,
        String userNovelDescription,
        float userNovelRating,
        ReadStatus userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate,
        List<String> keywords,
        List<PlatformGetResponse> platforms
) {
}