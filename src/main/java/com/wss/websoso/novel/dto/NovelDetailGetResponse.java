package com.wss.websoso.novel.dto;

import com.wss.websoso.platform.dto.PlatformGetResponse;

import java.util.List;

public record NovelDetailGetResponse(
        Long novelId,
        String novelTitle,
        String novelAuthor,
        String novelGenre,
        String novelImg,
        String novelDescription,
        List<PlatformGetResponse> platforms
) {
}
