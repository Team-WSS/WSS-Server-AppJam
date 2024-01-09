package com.wss.websoso.userNovel;

public record UserNovelResponse(
        Long userNovelId,
        String userNovelTitle,
        String userNovelAuthor,
        String userNovelGenre,
        String userNovelImg,
        float userNovelRating
) {
}
