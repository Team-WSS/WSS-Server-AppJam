package com.wss.websoso.novel;

import com.wss.websoso.novel.dto.NovelDetailGetResponse;
import com.wss.websoso.novel.dto.NovelsGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "작품 API", description = "작품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/novels")
public class NovelController {

    private final NovelService novelService;

    /*
    word(검색어)를 포함하는 소설들을 lastNovelId(마지막 소설 id)를 기준으로 size(개수)만큼 가져온다.
    word에 해당하는 소설이 없으면 빈 리스트를 반환한다.
     */
    @Operation(summary = "검색어로 작품 조회", description = "검색어가 포함된 작품 목록을 조회한다. (공백은 무시)")
    @Parameters({
            @Parameter(name = "lastNovelId", description = "마지막 작품 ID (첫 호출 시 9999)", required = true),
            @Parameter(name = "size", description = "한 번 호출로 작품 개수", required = true),
            @Parameter(name = "word", description = "검색어", required = true)
    })
    @GetMapping
    public ResponseEntity<NovelsGetResponse> getNovelsByWord(
            @RequestParam Long lastNovelId,
            @RequestParam int size,
            @RequestParam String word) {
        return ResponseEntity.ok().body(novelService.getNovelsByWord(lastNovelId, size, word));
    }

    @Operation(summary = "작품 단건 조회", description = "작품 단건 정보를 조회한다. (해당 작품이 이미 서재에 등록되어 있다면 서재 작품을 조회)")
    @Parameter(name = "novelId", description = "조회할 작품 ID", required = true)
    @GetMapping("/{novelId}")
    public ResponseEntity<NovelDetailGetResponse> getNovelByNovelId(@PathVariable Long novelId, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(novelService.getNovelByNovelId(novelId, userId));
    }
}