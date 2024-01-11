package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-novels")
public class UserNovelController {

    private final UserNovelService userNovelService;

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
}