package com.wss.websoso.memo.dto;

public record MemoGetResponse(
        Long memoId,
        String novelTitle,
        String memoContent,
        String memoDate
) {
}