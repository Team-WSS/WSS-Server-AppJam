package com.wss.websoso.userNovel;

public record UserNovelMemoResponse(
        Long memoId,
        String memoContent,
        String createdDate
) {
}