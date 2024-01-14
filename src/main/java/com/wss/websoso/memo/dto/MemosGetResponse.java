package com.wss.websoso.memo.dto;

import com.wss.websoso.memo.Memo;
import java.util.List;

public record MemosGetResponse(
        long memoCount,
        List<MemoGetResponse> memos
) {
    public static MemosGetResponse of(long memoCount, List<Memo> memos) {
        List<MemoGetResponse> memoList = memos.stream()
                .map(memo -> new MemoGetResponse(
                        memo.getMemoId(),
                        memo.getUserNovel().getUserNovelTitle(),
                        memo.getMemoContent(),
                        memo.getCreatedDate()
                )).toList();
        return new MemosGetResponse(memoCount, memoList);
    }
}
