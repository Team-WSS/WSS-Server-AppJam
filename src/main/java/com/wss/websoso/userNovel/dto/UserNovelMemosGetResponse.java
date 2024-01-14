package com.wss.websoso.userNovel.dto;

public record UserNovelMemosGetResponse(
        Long memoId,
        String memoContent,
        String createdDate
) {
}