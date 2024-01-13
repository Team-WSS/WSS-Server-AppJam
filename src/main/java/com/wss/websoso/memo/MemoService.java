package com.wss.websoso.memo;

import com.wss.websoso.memo.dto.MemoCreateRequest;
import com.wss.websoso.memo.dto.MemoCreateResponse;
import com.wss.websoso.memo.dto.MemoDetailGetResponse;
import com.wss.websoso.memo.dto.MemoUpdateRequest;
import com.wss.websoso.memo.dto.MemosGetResponse;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userNovel.UserNovel;
import com.wss.websoso.userNovel.UserNovelRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoService {

    public static final int DEFAULT_PAGE_NUMBER = 0;

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final UserNovelRepository userNovelRepository;

    @Transactional
    public MemoCreateResponse create(Long userId, Long userNovelId, MemoCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        UserNovel userNovel = userNovelRepository.findById(userNovelId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 작품이 서재에 없습니다."));

        if (userNovel.getUser() != user) {
            throw new IllegalArgumentException("내 서재의 작품이 아닙니다.");
        }

        if (request.memoContent().length() > 2000) {
            throw new IllegalArgumentException("memoContent의 최대 길이를 초과했습니다.");
        }

        memoRepository.save(Memo.builder()
                .memoContent(request.memoContent())
                .userNovel(userNovel)
                .build());

        user.updateUserWrittenMemoCount();

        boolean isAvatarUnlocked = user.getUserWrittenMemoCount() == 1 || user.getUserWrittenMemoCount() == 10;

        return MemoCreateResponse.of(isAvatarUnlocked);
    }

    public MemosGetResponse getMemos(Long userId, Long lastMemoId, int size, String sortType) {
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE_NUMBER, size);
        long memoCount = memoRepository.countByUserId(userId);
        if (Objects.equals(sortType, "NEWEST")) {
            Slice<Memo> entitySlice = memoRepository.findMemosByNewest(userId, lastMemoId, pageRequest);
            List<Memo> memos = entitySlice.getContent();
            return MemosGetResponse.of(memoCount, memos);
        } else {
            Slice<Memo> entitySlice = memoRepository.findMemosByOldest(userId, lastMemoId, pageRequest);
            List<Memo> memos = entitySlice.getContent();
            return MemosGetResponse.of(memoCount, memos);
        }
    }

    public MemoDetailGetResponse getMemo(Long memoId, Long userId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메모입니다."));

        if (memo.getUserNovel().getUser().getUserId() != userId) {
            throw new IllegalArgumentException("사용자의 메모가 아닙니다.");
        }

        return MemoDetailGetResponse.of(memo);
    }

    @Transactional
    public void deleteMemo(Long userId, Long memoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 메모입니다."));

        if (memo.getUserNovel().getUser() != user) {
            throw new IllegalArgumentException("사용자의 메모가 아닙니다.");
        }
        memoRepository.delete(memo);
    }

    @Transactional
    public void editMemo(Long userId, Long memoId, MemoUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 메모가 없습니다."));

        if (memo.getUserNovel().getUser() != user) {
            throw new IllegalArgumentException("사용자의 메모가 아닙니다.");
        }

        if (request.memoContent().length() > 2000) {
            throw new IllegalArgumentException("memoContent의 최대 길이를 초과했습니다.");
        }

        memo.updateContent(request.memoContent());
    }
}