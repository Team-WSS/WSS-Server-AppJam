package com.wss.websoso.userNovel.dto;

import com.wss.websoso.userNovel.UserNovel;
import java.util.List;

public record UserNovelsResponse(
        long userNovelCount,
        List<UserNovelResponse> userNovels
) {

    public static UserNovelsResponse of(long userNovelCount, List<UserNovel> userNovels) {
        List<UserNovelResponse> userNovelList = userNovels.stream()
                .map(userNovel -> new UserNovelResponse(
                        userNovel.getUserNovelId(),
                        userNovel.getUserNovelTitle(),
                        userNovel.getUserNovelImg(),
                        userNovel.getUserNovelAuthor(),
                        userNovel.getUserNovelRating()
                )).toList();

        return new UserNovelsResponse(userNovelCount, userNovelList);
    }
}
