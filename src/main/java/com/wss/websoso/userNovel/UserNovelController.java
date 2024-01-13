package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.memo.dto.MemoCreateRequest;
import com.wss.websoso.memo.dto.MemoCreateResponse;
import com.wss.websoso.memo.MemoService;
import com.wss.websoso.userNovel.dto.SosoPicksGetResponse;
import com.wss.websoso.userNovel.dto.UserNovelCreateRequest;
import com.wss.websoso.userNovel.dto.UserNovelMemoAndInfoGetResponse;
import com.wss.websoso.userNovel.dto.UserNovelUpdateRequest;
import com.wss.websoso.userNovel.dto.UserNovelsResponse;
import java.net.URI;
import java.security.Principal;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-novels")
public class UserNovelController {

    private final UserNovelService userNovelService;
    private final MemoService memoService;

    @PostMapping("/{novelId}")
    public ResponseEntity<Void> createUserNovel(
            @PathVariable Long novelId,
            @RequestBody UserNovelCreateRequest userNovelCreateRequest,
            Principal principal) {
        URI location = URI.create("/userNovels/" + userNovelService.createUserNovel(
                novelId,
                Long.valueOf(principal.getName()),
                userNovelCreateRequest)
        );

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{userNovelId}/memo")
    public ResponseEntity<MemoCreateResponse> createMemo(
            @PathVariable Long userNovelId,
            @RequestBody MemoCreateRequest request,
            Principal principal
    ) {
        MemoCreateResponse response = memoService.create(Long.valueOf(principal.getName()), userNovelId, request);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<UserNovelsResponse> getUserNovels(
            @RequestParam String readStatus,
            @RequestParam Long lastUserNovelId,
            @RequestParam int size,
            @RequestParam String sortType,
            Principal principal) {

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

    @GetMapping("/{userNovelId}")
    public ResponseEntity<UserNovelMemoAndInfoGetResponse> getUserNovelMemoAndInfo(@PathVariable Long userNovelId,
                                                                                   Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userNovelService.getUserNovelMemoAndInfo(userId, userNovelId));
    }

    @GetMapping("/soso-picks")
    public ResponseEntity<SosoPicksGetResponse> getSosoPicks() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userNovelService.getSosoPicks());
    }

    @DeleteMapping("/{userNovelId}")
    public ResponseEntity<Void> deleteUserNovel(@PathVariable Long userNovelId,
                                                Principal principal) {

        Long userId = Long.valueOf(principal.getName());
        userNovelService.deleteUserNovel(userId, userNovelId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

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