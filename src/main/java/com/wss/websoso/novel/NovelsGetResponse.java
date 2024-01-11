package com.wss.websoso.novel;

import java.util.List;

public record NovelsGetResponse(
        List<NovelGetResponse> novelList
) {
}
