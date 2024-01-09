package com.wss.websoso.memo;

public record MemoGetResponse(
        Long memoId,
        String novelTitle,
        String memoContent,
        String memoDate
) {
}