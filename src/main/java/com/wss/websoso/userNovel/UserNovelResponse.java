package com.wss.websoso.userNovel;

public record UserNovelResponse(
        Long userNovelId,
        String userNovelTitle,
        String userNovelImg,
        String userNovelAuthor,
        float userNovelRating
) {
}
