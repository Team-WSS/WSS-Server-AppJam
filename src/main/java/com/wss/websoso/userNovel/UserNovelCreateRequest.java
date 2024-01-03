package com.wss.websoso.userNovel;

import java.util.List;

public record UserNovelCreateRequest(
        float novelRating,
        String novelReadStatus,
        String novelReadStartDate,
        String novelReadEndDate,
        List<Long> keywordIds
) {
}