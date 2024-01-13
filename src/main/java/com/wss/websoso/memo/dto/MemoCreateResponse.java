package com.wss.websoso.memo.dto;

public record MemoCreateResponse(
        Boolean isAvatarUnlocked
) {
    public static MemoCreateResponse of(Boolean isAvatarUnlocked) {
        return new MemoCreateResponse(
                isAvatarUnlocked
        );
    }
}
