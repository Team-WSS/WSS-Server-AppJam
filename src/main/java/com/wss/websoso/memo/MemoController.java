package com.wss.websoso.memo;

import com.wss.websoso.memo.dto.MemoDetailGetResponse;
import com.wss.websoso.memo.dto.MemoUpdateRequest;
import com.wss.websoso.memo.dto.MemosGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@Tag(name = "메모 API", description = "메모 관련 API")
@RestController
@RequestMapping("/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 서재 메모 전체 조회
    @Operation(summary = "유저 기록 전체 조회", description = "유저가 작성한 메모 전체를 조회한다.")
    @Parameter(name = "avatarId", description = "조회할 아바타 ID", required = true)
    @GetMapping
    public MemosGetResponse getMemos(
            @RequestParam Long lastMemoId,
            @RequestParam int size,
            @RequestParam String sortType,
            Principal principal) {
        return memoService.getMemos(Long.valueOf(principal.getName()), lastMemoId, size, sortType);
    }

    @Operation(summary = "메모 단건 정보 조회", description = "메모 단건 정보를 조회한다.")
    @Parameter(name = "memoId", description = "조회할 메모 ID", required = true)
    @GetMapping("/{memoId}")
    public ResponseEntity<MemoDetailGetResponse> getMemo(@PathVariable Long memoId, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memoService.getMemo(memoId, userId));
    }

    // 삭제
    @Operation(summary = "메모 삭제", description = "메모를 삭제한다.")
    @Parameter(name = "memoId", description = "삭제할 메모 ID", required = true)
    @DeleteMapping("/{memoId}")
    public ResponseEntity deleteMemo(@PathVariable Long memoId, Principal principal) {
        try {
            memoService.deleteMemo(Long.valueOf(principal.getName()), memoId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if ("사용자의 메모가 아닙니다.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "사용자의 메모가 아닙니다."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "예상치 못한 오류가 발생했습니다."));
            }
        }
    }

    // 수정
    @Operation(summary = "메모 정보 수정", description = "메모의 정보를 수정한다.")
    @Parameters({
            @Parameter(name = "memoId", description = "수정할 메모 ID", required = true),
            @Parameter(name = "request", description = "변경할 메모 내용", required = true)
    })
    @PatchMapping("/{memoId}")
    public ResponseEntity<Map<String, String>> editMemo(
            @PathVariable Long memoId,
            @RequestBody MemoUpdateRequest request,
            Principal principal
    ) {
        try {
            memoService.editMemo(Long.valueOf(principal.getName()), memoId, request);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            if ("사용자의 메모가 아닙니다.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "사용자의 메모가 아닙니다."));
            } else if ("memoContent의 최대 길이를 초과했습니다.".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "memoContent의 최대 길이를 초과했습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("message", "예상치 못한 오류가 발생했습니다."));
            }
        }
    }
}