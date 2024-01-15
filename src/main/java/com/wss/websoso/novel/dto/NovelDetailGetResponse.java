package com.wss.websoso.novel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.platform.dto.PlatformGetResponse;

import java.util.List;

public record NovelDetailGetResponse(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long novelId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Long userNovelId,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String novelTitle,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelTitle,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String novelAuthor,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelAuthor,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String novelGenre,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelGenre,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String novelImg,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelImg,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String novelDescription,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String userNovelDescription,
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
