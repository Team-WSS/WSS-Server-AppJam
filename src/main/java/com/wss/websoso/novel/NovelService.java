package com.wss.websoso.novel;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;

    public List<Novel> getNovelsByWord(Long lastNovelId, int size, String word) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Slice<Novel> entitySlice = novelRepository.findByIdLessThanOrderByIdDesc(lastNovelId, pageRequest, word);
        List<Novel> entityList = entitySlice.getContent();
        return entityList;
    }
}
