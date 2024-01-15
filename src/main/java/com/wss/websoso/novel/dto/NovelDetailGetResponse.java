package com.wss.websoso.novel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.platform.dto.PlatformGetResponse;

import java.util.List;

public record NovelDetailGetResponse(
        Long novelId,
        String novelTitle,
        String novelAuthor,
        String novelGenre,
        String novelImg,
        String novelDescription,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Float userNovelRating,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        ReadStatus userNovelReadStatus,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelReadStartDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelReadEndDate,
        List<PlatformGetResponse> platforms
) {
}
