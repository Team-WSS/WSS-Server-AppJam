package com.wss.websoso.userNovel;

public record UserNovelUpdateRequest(
        float userNovelRating,
        String userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate
) {
}
