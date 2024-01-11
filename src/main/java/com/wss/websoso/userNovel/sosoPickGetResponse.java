package com.wss.websoso.userNovel;

import com.wss.websoso.novel.Novel;

public record sosoPickGetResponse(
        String novelImg,
        String novelTitle,
        String novelAuthor,
        Long novelRegisteredCount
) {
    public static sosoPickGetResponse of(Novel novel, Long novelRegisteredCount) {
        return new sosoPickGetResponse(novel.getNovelImg(), novel.getNovelTitle(), novel.getNovelAuthor(), novelRegisteredCount);
    }
}
