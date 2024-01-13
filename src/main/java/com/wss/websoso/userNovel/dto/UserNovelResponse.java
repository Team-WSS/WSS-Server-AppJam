package com.wss.websoso.userNovel.dto;

public record UserNovelResponse(
        Long userNovelId,
        String userNovelTitle,
        String userNovelImg,
        String userNovelAuthor,
        float userNovelRating
) {
}
