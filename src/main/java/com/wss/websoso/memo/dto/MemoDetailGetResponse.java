package com.wss.websoso.memo.dto;

import com.wss.websoso.memo.Memo;

public record MemoDetailGetResponse(
        String userNovelTitle,
        String userNovelImg,
        String userNovelAuthor,
        String memoDate,
        String memoContent
) {
    public static MemoDetailGetResponse of(Memo memo) {
        return new MemoDetailGetResponse(
                memo.getUserNovel().getUserNovelTitle(),
                memo.getUserNovel().getUserNovelImg(),
                memo.getUserNovel().getUserNovelAuthor(),
                memo.getCreatedDate(),
                memo.getMemoContent()
        );
    }
}
