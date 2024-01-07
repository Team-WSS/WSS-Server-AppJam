package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNovelService {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private final UserNovelRepository userNovelRepository;

    // ALL
    public UserNovelsResponse getUserNovels(Long lastUserNovelId, int size, String sortType) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        long userNovelCount = userNovelRepository.count();

        if (Objects.equals(sortType, "NEWEST")) {
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByNewest(lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        } else {      // OLDEST
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByOldest(lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        }
    }

    // [FINISH, READING, DROP, WISH]
    public UserNovelsResponse getUserNovels(ReadStatus readStatus, Long lastUserNovelId, int size, String sortType) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        long userNovelCount = userNovelRepository.countByUserNovelReadStatus(readStatus);

        if (Objects.equals(sortType, "NEWEST")) {
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByReadStatusAndNewest(readStatus, lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        } else {      // OLDEST
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByReadStatusAndOldest(readStatus, lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        }
    }
}
