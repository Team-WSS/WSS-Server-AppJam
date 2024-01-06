package com.wss.websoso.novel;

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
        Slice<Novel> entitySlice = novelRepository.findByIdLessThanOrderByIdDesc(lastNovelId, pageRequest, word.replaceAll(" ", ""));
        return entitySlice.getContent();
    }
}
