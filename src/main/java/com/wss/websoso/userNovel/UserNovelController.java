package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-novels")
public class UserNovelController {

    private final UserNovelService userNovelService;

    @PostMapping("{novelId}")
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

    @GetMapping
    public UserNovelsResponse getUserNovels(
            @RequestParam String readStatus,
            @RequestParam Long lastUserNovelId,
            @RequestParam int size,
            @RequestParam String sortType,
            Principal principal) {

        Long userId = Long.valueOf(principal.getName());

        if (Objects.equals(readStatus, "ALL")) {
            return userNovelService.getUserNovels(userId, lastUserNovelId, size, sortType);
        } else {        // OLDEST
            return userNovelService.getUserNovels(userId, ReadStatus.valueOf(readStatus),
                    lastUserNovelId, size, sortType);
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