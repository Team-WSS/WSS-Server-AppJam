package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.memo.MemoService;
import com.wss.websoso.memo.dto.MemoCreateRequest;
import com.wss.websoso.memo.dto.MemoCreateResponse;
import com.wss.websoso.userNovel.dto.SosoPicksGetResponse;
import com.wss.websoso.userNovel.dto.UserNovelCreateRequest;
import com.wss.websoso.userNovel.dto.UserNovelMemoAndInfoGetResponse;
import com.wss.websoso.userNovel.dto.UserNovelUpdateRequest;
import com.wss.websoso.userNovel.dto.UserNovelsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "서재 작품 API", description = "서재 작품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-novels")
public class UserNovelController {

    private final UserNovelService userNovelService;
    private final MemoService memoService;

    @Operation(summary = "작품 등록", description = "작품을 내 서재에 등록한다.")
    @Parameters({
            @Parameter(name = "novelId", description = "등록할 작품 ID", required = true),
            @Parameter(name = "userNovelCreateRequest", description = "서재에 등록할 정보", required = true)
    })
    @PostMapping("/{novelId}")
    public ResponseEntity<Map<String, Long>> createUserNovel(
            @PathVariable Long novelId,
            @RequestBody UserNovelCreateRequest userNovelCreateRequest,
            Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        Long userNovelId = userNovelService.createUserNovel(novelId, userId, userNovelCreateRequest);
        URI location = URI.create("/userNovels/" + userNovelId);

        return ResponseEntity.created(location).body(Map.of("userNovelId", userNovelId));
    }

    @Operation(summary = "메모 생성", description = "서재 작품에 메모를 생성한다.")
    @Parameters({
            @Parameter(name = "userNovelId", description = "서재 작품 ID", required = true),
            @Parameter(name = "memoCreateRequest", description = "생성할 메모 내용", required = true)
    })
    @PostMapping("/{userNovelId}/memo")
    public ResponseEntity<MemoCreateResponse> createMemo(
            @PathVariable Long userNovelId,
            @RequestBody MemoCreateRequest memoCreateRequest,
            Principal principal
    ) {
        Long userId = Long.valueOf(principal.getName());
        MemoCreateResponse memoCreateResponse = memoService.createMemo(userId, userNovelId, memoCreateRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memoCreateResponse);
    }

    @Operation(summary = "서재 작품 조회", description = "서재 작품 전체를 조회한다.")
    @Parameters({
            @Parameter(name = "readStatus", description = "읽은 상태", required = true),
            @Parameter(name = "lastUserNovelId", description = "마지막 서재 작품 ID (최신순:9999, 오래된순:0)", required = true),
            @Parameter(name = "size", description = "한 번 호출로 서재 작품 개수", required = true),
            @Parameter(name = "sortType", description = "NEWEST(최신순) | OLDEST(오래된순)", required = true)
    })
    @GetMapping
    public ResponseEntity<UserNovelsResponse> getUserNovels(
            @RequestParam String readStatus,
            @RequestParam Long lastUserNovelId,
            @RequestParam int size,
            @RequestParam String sortType,
            Principal principal
    ) {

        Long userId = Long.valueOf(principal.getName());

        if (Objects.equals(readStatus, "ALL")) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userNovelService.getUserNovels(userId, lastUserNovelId, size, sortType));
        } else {        // OLDEST
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userNovelService.getUserNovels(userId, ReadStatus.valueOf(readStatus),
                            lastUserNovelId, size, sortType));
        }
    }

    @Operation(summary = "서재 작품 메모&정보 조회", description = "서재 작품의 메모와 정보를 조회한다.")
    @Parameter(name = "userNovelId", description = "조회할 서재 작품 ID", required = true)
    @GetMapping("/{userNovelId}")
    public ResponseEntity<UserNovelMemoAndInfoGetResponse> getUserNovelMemoAndInfo(@PathVariable Long userNovelId,
                                                                                   Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userNovelService.getUserNovelMemoAndInfo(userId, userNovelId));
    }

    @Operation(summary = "소소's pick 조회", description = "유저가 등록한 작품의 정보를 최신순으로 10개 조회한다. (중복X)")
    @GetMapping("/soso-picks")
    public ResponseEntity<SosoPicksGetResponse> getSosoPicks() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userNovelService.getSosoPicks());
    }

    @Operation(summary = "서재 작품 삭제", description = "서재 작품을 삭제한다.")
    @Parameter(name = "userNovelId", description = "삭제할 서재 작품 ID", required = true)
    @DeleteMapping("/{userNovelId}")
    public ResponseEntity<Void> deleteUserNovel(@PathVariable Long userNovelId,
                                                Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        userNovelService.deleteUserNovel(userId, userNovelId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "서재 작품 정보 변경", description = "서재 작품의 정보를 변경한다.")
    @Parameters({
            @Parameter(name = "userNovelId", description = "삭제할 서재 작품 ID", required = true),
            @Parameter(name = "userNovelUpdateRequest", description = "변경할 서재 작품 정보", required = true)
    })
    @PatchMapping("/{userNovelId}")
    public ResponseEntity<Void> updateUserNovel(@PathVariable Long userNovelId,
                                                @RequestBody UserNovelUpdateRequest userNovelUpdateRequest,
                                                Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        userNovelService.updateUserNovel(userId, userNovelId, userNovelUpdateRequest);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}