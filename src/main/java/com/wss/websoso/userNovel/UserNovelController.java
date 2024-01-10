package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
    public UserNovelMemoAndInfoGetResponse getUserNovelMemoAndInfo(@PathVariable Long userNovelId,
                                                                   Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        return userNovelService.getUserNovelMemoAndInfo(userId, userNovelId);
    }
}