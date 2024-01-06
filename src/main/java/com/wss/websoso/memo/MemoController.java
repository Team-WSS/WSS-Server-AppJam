package com.wss.websoso.memo;

import java.net.URI;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 생성
    @PostMapping("{userNovelId}")
    public ResponseEntity<Map<String, String>> createMemo(
            @PathVariable Long userNovelId,
            @RequestBody MemoCreateRequest request,
            Principal principal
    ) {
        try {
            URI location = URI.create(memoService.create(Long.valueOf(principal.getName()), userNovelId, request));
            return ResponseEntity.created(location).build();
        } catch (IllegalArgumentException e) {
            if ("memoContent의 최대 길이를 초과했습니다.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "memoContent의 최대 길이를 초과했습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "예상치 못한 오류가 발생했습니다."));
            }
        }
    }
}
