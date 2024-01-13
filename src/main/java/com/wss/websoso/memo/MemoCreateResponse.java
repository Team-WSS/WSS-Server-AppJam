package com.wss.websoso.memo;

public record MemoCreateResponse(
        Boolean avatarUnlocked
) {
    public static MemoCreateResponse of(Boolean avatarUnlocked) {
        return new MemoCreateResponse(
                avatarUnlocked
        );
    }
}
