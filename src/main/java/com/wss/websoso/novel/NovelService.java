package com.wss.websoso.novel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.keyword.Keyword;
import com.wss.websoso.keyword.KeywordRepository;
import com.wss.websoso.platform.Platform;
import com.wss.websoso.platform.PlatformGetResponse;
import com.wss.websoso.platform.PlatformRepository;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userNovel.UserNovel;
import com.wss.websoso.userNovel.UserNovelCreateRequest;
import com.wss.websoso.userNovel.UserNovelGetResponse;
import com.wss.websoso.userNovel.UserNovelRepository;
import com.wss.websoso.userNovelKeyword.UserNovelKeyword;
import com.wss.websoso.userNovelKeyword.UserNovelKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NovelService {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    private final NovelRepository novelRepository;
    private final UserRepository userRepository;
    private final UserNovelRepository userNovelRepository;
    private final UserNovelKeywordRepository userNovelKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final PlatformRepository platformRepository;

    public NovelsGetResponse getNovelsByWord(Long lastNovelId, int size, String word) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        Slice<Novel> entitySlice = novelRepository.findByIdLessThanOrderByIdDesc(lastNovelId, pageRequest, word.replaceAll(" ", ""));
        return new NovelsGetResponse(entitySlice.getContent().stream()
                .map(novel -> new NovelGetResponse(
                        novel.getNovelId(),
                        novel.getNovelTitle(),
                        novel.getNovelAuthor(),
                        novel.getNovelGenre(),
                        novel.getNovelImg()
                ))
                .toList());
    }

    public ResponseEntity<?> getNovelByNovelId(Long novelId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작품이 없습니다."));

        try {
            UserNovel userNovel = userNovelRepository.findByUserAndNovelId(user, novelId)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 등록된 작품이 없습니다."));

            return ResponseEntity.ok(new UserNovelGetResponse(
                    userNovel.getUserNovelId(),
                    userNovel.getUserNovelTitle(),
                    userNovel.getUserNovelAuthor(),
                    userNovel.getUserNovelGenre(),
                    userNovel.getUserNovelImg(),
                    userNovel.getUserNovelDescription(),
                    userNovel.getUserNovelRating(),
                    userNovel.getUserNovelReadStatus(),
                    userNovel.getUserNovelReadStartDate(),
                    userNovel.getUserNovelReadEndDate(),
                    userNovel.getUserNovelKeywords().stream()
                            .map(userNovelKeyword -> userNovelKeyword.getKeyword().getKeywordName())
                            .toList(),
                    userNovel.getPlatforms().stream()
                            .map(platform -> new PlatformGetResponse(
                                    platform.getPlatformName(),
                                    platform.getPlatformUrl()
                            ))
                            .toList()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(new NovelDetailGetResponse(
                    novel.getNovelId(),
                    novel.getNovelTitle(),
                    novel.getNovelAuthor(),
                    novel.getNovelGenre(),
                    novel.getNovelImg(),
                    novel.getNovelDescription(),
                    novel.getPlatforms().stream()
                            .map(platform -> new PlatformGetResponse(
                                    platform.getPlatformName(),
                                    platform.getPlatformUrl()
                            ))
                            .toList()
            ));
        }
    }

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
                .userNovelRating(userNovelCreateRequest.novelRating())
                .userNovelReadStatus(ReadStatus.valueOf(userNovelCreateRequest.novelReadStatus()))
                .userNovelReadStartDate(userNovelCreateRequest.novelReadStartDate())
                .userNovelReadEndDate(userNovelCreateRequest.novelReadEndDate())
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

        if (userNovelCreateRequest.keywordNames() != null) {
            userNovelCreateRequest.keywordNames().stream()
                    .map(keywordName -> {
                        Optional<Keyword> optionalKeyword = keywordRepository.findByKeywordName(keywordName);
                        if (optionalKeyword.isEmpty()) {
                            throw new IllegalArgumentException("해당하는 키워드가 없습니다.");
                        }
                        return optionalKeyword.get();
                    })
                    .forEach(keyword -> {
                        UserNovelKeyword userNovelKeyword = UserNovelKeyword.builder()
                                .userNovel(userNovel)
                                .keyword(keyword)
                                .build();

                        userNovelKeywordRepository.save(userNovelKeyword);
                    });
        }

        return userNovel.getUserNovelId();
    }
}