package com.wss.websoso.novel;

import com.wss.websoso.platform.PlatformGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelService {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    private final NovelRepository novelRepository;

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
}