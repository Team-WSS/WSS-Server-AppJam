package com.wss.websoso.novel;

import com.wss.websoso.userNovel.UserNovelCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {

    private final NovelService novelService;

    /*
    word(검색어)를 포함하는 소설들을 lastNovelId(마지막 소설 id)를 기준으로 size(개수)만큼 가져온다.
    word에 해당하는 소설이 없으면 빈 리스트를 반환한다.
     */
    @GetMapping
    public List<NovelGetResponse> getNovelsByWord(
            @RequestParam Long lastNovelId,
            @RequestParam int size,
            @RequestParam String word) {
        return novelService.getNovelsByWord(lastNovelId, size, word);
    }

    @GetMapping("{novelId}")
    public ResponseEntity getNovelByNovelId(@PathVariable Long novelId, Principal principal) {
        return novelService.getNovelByNovelId(novelId, Long.valueOf(principal.getName()));
    }

    @PostMapping("{novelId}")
    public ResponseEntity<Void> createUserNovel(
            @PathVariable Long novelId,
            @RequestBody UserNovelCreateRequest userNovelCreateRequest,
            Principal principal) {
        URI location = URI.create("/userNovels/" + novelService.createUserNovel(
                novelId,
                Long.valueOf(principal.getName()),
                userNovelCreateRequest)
        );

        return ResponseEntity.created(location).build();
    }
}