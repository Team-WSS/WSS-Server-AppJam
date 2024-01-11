package com.wss.websoso.userNovel;

public record UserNovelCreateRequest(
        float userNovelRating,
        String userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate
) {
}