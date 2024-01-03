package com.wss.websoso.novel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.keyword.Keyword;
import com.wss.websoso.keyword.KeywordRepository;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userNovel.UserNovel;
import com.wss.websoso.userNovel.UserNovelCreateRequest;
import com.wss.websoso.userNovel.UserNovelRepository;
import com.wss.websoso.userNovelKeyword.UserNovelKeyword;
import com.wss.websoso.userNovelKeyword.UserNovelKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Novel> getNovelsByWord(Long lastNovelId, int size, String word) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        Slice<Novel> entitySlice = novelRepository.findByIdLessThanOrderByIdDesc(lastNovelId, pageRequest, word);
        return entitySlice.getContent();
    }

    public NovelGetResponse getNovelByNovelId(Long novelId) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작품이 없습니다."));

        return new NovelGetResponse(
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
        );
    }

    public Long createUserNovel(Long novelId, Long userId, UserNovelCreateRequest userNovelCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작품이 없습니다."));

        UserNovel userNovel = UserNovel.builder()
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

        if (userNovelCreateRequest.keywordIds() != null) {
            userNovelCreateRequest.keywordIds().stream()
                    .map(keywordId -> {
                        Optional<Keyword> optionalKeyword = keywordRepository.findById(keywordId);
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

    public void deleteUserNovel(Long userNovelId) {
        userNovelRepository.deleteById(userNovelId);
    }
}