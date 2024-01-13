package com.wss.websoso.userNovel.dto;

public record UserNovelUpdateRequest(
        float userNovelRating,
        String userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate
) {
}
