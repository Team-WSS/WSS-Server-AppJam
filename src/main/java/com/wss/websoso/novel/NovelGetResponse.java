package com.wss.websoso.novel;

public record NovelGetResponse(
        Long novelId,
        String novelTitle,
        String novelAuthor,
        String novelGenre,
        String novelImg
) {
}
