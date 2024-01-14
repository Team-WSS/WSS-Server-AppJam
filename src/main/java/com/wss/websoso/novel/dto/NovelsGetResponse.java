package com.wss.websoso.novel.dto;

import java.util.List;

public record NovelsGetResponse(
        List<NovelGetResponse> novels
) {
}
