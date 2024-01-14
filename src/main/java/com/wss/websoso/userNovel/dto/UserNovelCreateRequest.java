package com.wss.websoso.userNovel.dto;

public record UserNovelCreateRequest(
        float userNovelRating,
        String userNovelReadStatus,
        String userNovelReadStartDate,
        String userNovelReadEndDate
) {
}