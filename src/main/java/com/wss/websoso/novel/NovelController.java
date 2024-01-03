package com.wss.websoso.novel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {

    private final NovelService novelService;
    private final NovelRepository novelRepository;

    /*
    word(검색어)를 포함하는 소설들을 lastNovelId(마지막 소설 id)를 기준으로 size(개수)만큼 가져온다.
    word에 해당하는 소설이 없으면 빈 리스트를 반환한다.
     */
    @GetMapping
    public List<Novel> getNovelsByWord(
            @RequestParam Long lastNovelId,
            @RequestParam int size,
            @RequestParam String word) {
        return novelService.getNovelsByWord(lastNovelId, size, word);
    }

    @GetMapping("{novelId}")
    public NovelGetResponse getNovelByNovelId(@PathVariable Long novelId) {
        return novelService.getNovelByNovelId(novelId);
    }
}