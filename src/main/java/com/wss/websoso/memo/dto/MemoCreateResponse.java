package com.wss.websoso.memo.dto;

public record MemoCreateResponse(
        boolean isAvatarUnlocked
) {
    public static MemoCreateResponse of(boolean isAvatarUnlocked) {
        return new MemoCreateResponse(
                isAvatarUnlocked
        );
    }
}
