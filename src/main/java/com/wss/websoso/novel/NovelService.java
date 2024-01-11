package com.wss.websoso.novel;

import com.wss.websoso.platform.PlatformGetResponse;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userNovel.UserNovel;
import com.wss.websoso.userNovel.UserNovelGetResponse;
import com.wss.websoso.userNovel.UserNovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelService {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    private final NovelRepository novelRepository;
    private final UserRepository userRepository;
    private final UserNovelRepository userNovelRepository;

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
}