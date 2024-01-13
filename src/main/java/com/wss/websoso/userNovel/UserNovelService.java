package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.genreBadge.GenreBadge;
import com.wss.websoso.genreBadge.GenreBadgeRepository;
import com.wss.websoso.memo.Memo;
import com.wss.websoso.memo.MemoRepository;
import com.wss.websoso.novel.Novel;
import com.wss.websoso.novel.NovelRepository;
import com.wss.websoso.platform.Platform;
import com.wss.websoso.platform.PlatformRepository;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserNovelService {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private final UserNovelRepository userNovelRepository;
    private final MemoRepository memoRepository;
    private final PlatformRepository platformRepository;
    private final UserRepository userRepository;
    private final NovelRepository novelRepository;
    private final GenreBadgeRepository genreBadgeRepository;

    @Transactional
    public Long createUserNovel(Long novelId, Long userId, UserNovelCreateRequest userNovelCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작품이 없습니다."));

        UserNovel userNovel = UserNovel.builder()
                .novelId(novelId)
                .userNovelTitle(novel.getNovelTitle())
                .userNovelAuthor(novel.getNovelAuthor())
                .userNovelGenre(novel.getNovelGenre())
                .userNovelImg(novel.getNovelImg())
                .userNovelDescription(novel.getNovelDescription())
                .userNovelRating(userNovelCreateRequest.userNovelRating())
                .userNovelReadStatus(ReadStatus.valueOf(userNovelCreateRequest.userNovelReadStatus()))
                .userNovelReadStartDate(userNovelCreateRequest.userNovelReadStartDate())
                .userNovelReadEndDate(userNovelCreateRequest.userNovelReadEndDate())
                .user(user)
                .build();

        userNovelRepository.save(userNovel);

        if (novel.getPlatforms() != null) {
            novel.getPlatforms().stream()
                    .forEach(platform -> {
                        Platform newPlatform = Platform.builderWithUserNovel()
                                .platformName(platform.getPlatformName())
                                .platformUrl(platform.getPlatformUrl())
                                .userNovel(userNovel)
                                .buildWithUserNovel();

                        platformRepository.save(newPlatform);
                    });
        }

        return userNovel.getUserNovelId();
    }

    // ALL
    public UserNovelsResponse getUserNovels(Long userId, Long lastUserNovelId, int size, String sortType) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        long userNovelCount = userNovelRepository.countByUserId(userId);

        if (Objects.equals(sortType, "NEWEST")) {
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByNewest(userId, lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        } else {      // OLDEST
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByOldest(userId, lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        }
    }

    // [FINISH, READING, DROP, WISH]
    public UserNovelsResponse getUserNovels(Long userId, ReadStatus readStatus,
                                            Long lastUserNovelId, int size, String sortType) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        long userNovelCount = userNovelRepository.countByUserNovelReadStatus(userId, readStatus);

        if (Objects.equals(sortType, "NEWEST")) {
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByReadStatusAndNewest(userId, readStatus, lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        } else {      // OLDEST
            Slice<UserNovel> entitySlice = userNovelRepository.
                    findUserNovelsByReadStatusAndOldest(userId, readStatus, lastUserNovelId, pageRequest);
            List<UserNovel> userNovels = entitySlice.getContent();

            return UserNovelsResponse.of(userNovelCount, userNovels);
        }
    }

    public UserNovelMemoAndInfoGetResponse getUserNovelMemoAndInfo(Long userId, Long userNovelId) {

        UserNovel userNovelForAuthorization = userNovelRepository.findById(userNovelId)
                .orElseThrow(() -> new RuntimeException("해당하는 userNovel이 없습니다."));

        Long userIdForAuthorization = userNovelForAuthorization.getUser().getUserId();
        if (!Objects.equals(userIdForAuthorization, userId)) {
            throw new RuntimeException("잘못된 접근입니다.(인가X)");
        }

        List<Memo> memos = memoRepository.findByUserNovelId(userNovelId);
        List<Platform> platforms = platformRepository.findByUserNovelId(userNovelId);
        UserNovel userNovel = userNovelRepository.findByUserNovelId(userNovelId);
        GenreBadge genreBadge = genreBadgeRepository.findByGenreBadgeName(userNovel.getUserNovelGenre());

        return UserNovelMemoAndInfoGetResponse.of(memos, userNovel, platforms, genreBadge);
    }

    public SosoPicksGetResponse getSosoPicks() {
        Long lastUserNovelId;
        try {
            lastUserNovelId = userNovelRepository.findByMaxUserNovelId()
                    .orElseThrow(() -> new RuntimeException("유저가 등록한 서재 작품이 없습니다."));
        } catch (Exception e) {
            return new SosoPicksGetResponse(new ArrayList<>());
        }
        List<Novel> recentNovels = new ArrayList<>();
        int count = 0;
        while (count < 10 && lastUserNovelId >= 0) {
            Optional<UserNovel> userNovel = userNovelRepository.findById(lastUserNovelId);

            if (!userNovel.isEmpty()) {
                Novel novel = novelRepository.findById(userNovel.get().getNovelId())
                        .orElseThrow(() -> new RuntimeException("해당하는 작품이 없습니다."));

                if (!recentNovels.contains(novel)) {
                    recentNovels.add(novel);
                    count++;
                }
            }

            lastUserNovelId--;
        }

        List<sosoPickGetResponse> sosoPicks = recentNovels.stream()
                .map(novel -> sosoPickGetResponse.of(novel, userNovelRepository.countUserNovelsByNovelId(novel.getNovelId())))
                .toList();

        return new SosoPicksGetResponse(sosoPicks);
    }

    @Transactional
    public void deleteUserNovel(Long userId, Long userNovelId) {

        UserNovel userNovelForAuthorization = userNovelRepository.findById(userNovelId)
                .orElseThrow(() -> new RuntimeException("해당하는 userNovel이 없습니다."));

        Long userIdForAuthorization = userNovelForAuthorization.getUser().getUserId();
        if (!Objects.equals(userIdForAuthorization, userId)) {
            throw new RuntimeException("잘못된 접근입니다.(인가X)");
        }

        UserNovel userNovel = userNovelRepository.findByUserNovelId(userNovelId);

        userNovelRepository.delete(userNovel);
    }

    @Transactional
    public void updateUserNovel(Long userId, Long userNovelId, UserNovelUpdateRequest userNovelUpdateRequest) {
        UserNovel userNovelForAuthorization = userNovelRepository.findById(userNovelId)
                .orElseThrow(() -> new RuntimeException("해당하는 userNovel이 없습니다."));

        Long userIdForAuthorization = userNovelForAuthorization.getUser().getUserId();
        if (!Objects.equals(userIdForAuthorization, userId)) {
            throw new RuntimeException("잘못된 접근입니다.(인가X)");
        }

        UserNovel userNovel = userNovelRepository.findByUserNovelId(userNovelId);
        userNovel.update(
                userNovelUpdateRequest.userNovelRating(),
                userNovelUpdateRequest.userNovelReadStatus(),
                userNovelUpdateRequest.userNovelReadStartDate(),
                userNovelUpdateRequest.userNovelReadEndDate());
    }
}