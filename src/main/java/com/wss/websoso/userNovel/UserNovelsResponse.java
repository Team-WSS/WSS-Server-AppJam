package com.wss.websoso.userNovel;

import java.util.List;

public record UserNovelsResponse(
        long userNovelCount,
        List<UserNovelResponse> userNovelList
) {

    public static UserNovelsResponse of(long userNovelCount, List<UserNovel> userNovels) {
        List<UserNovelResponse> userNovelList = userNovels.stream()
                .map(userNovel -> new UserNovelResponse(
                        userNovel.getUserNovelId(),
                        userNovel.getUserNovelAuthor(),
                        userNovel.getUserNovelGenre(),
                        userNovel.getUserNovelImg(),
                        userNovel.getUserNovelRating()
                )).toList();

        return new UserNovelsResponse(userNovelCount, userNovelList);
    }
}
