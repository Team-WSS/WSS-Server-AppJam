package com.wss.websoso.userNovel.dto;

import com.wss.websoso.novel.Novel;

public record SosoPickGetResponse(
        String novelImg,
        String novelTitle,
        String novelAuthor,
        Long novelRegisteredCount
) {
    public static SosoPickGetResponse of(Novel novel, Long novelRegisteredCount) {
        return new SosoPickGetResponse(novel.getNovelImg(), novel.getNovelTitle(), novel.getNovelAuthor(),
                novelRegisteredCount);
    }
}
