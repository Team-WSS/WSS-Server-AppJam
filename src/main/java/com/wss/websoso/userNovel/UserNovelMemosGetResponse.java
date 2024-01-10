package com.wss.websoso.userNovel;

public record UserNovelMemosGetResponse(
        Long memoId,
        String memoContent,
        String createdDate
) {
}