package com.wss.websoso.novel;

import com.wss.websoso.platform.PlatformGetResponse;

import java.util.List;

public record NovelGetResponse(
        Long novelId,
        String novelTitle,
        String novelAuthor,
        String novelGenre,
        String novelImg,
        String novelDescription,
        List<PlatformGetResponse> platforms
) {
}
