package com.wss.websoso.userNovel;

public record UserNovelResponse(
        Long userNovelId,
        String userNovelAuthor,
        String userNovelGenre,
        String userNovelImg,
        float userNovelRating
) {
}
