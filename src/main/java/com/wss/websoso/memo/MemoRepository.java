package com.wss.websoso.memo;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    // NEWEST
    @Query(value = "SELECT m FROM Memo m WHERE "
            + "m.userNovel.user.userId = ?1 AND "
            + "m.memoId < ?2 "
            + "ORDER BY m.memoId DESC")
    Slice<Memo> findMemosByNewest(Long userId, Long lastMemoId, PageRequest pageRequest);

    // OLDEST
    @Query(value = "SELECT m FROM Memo m WHERE "
            + "m.userNovel.user.userId = ?1 AND "
            + "m.memoId > ?2 ")
    Slice<Memo> findMemosByOldest(Long userId, Long lastMemoId, PageRequest pageRequest);

    @Query(value = "SELECT COUNT(m) FROM Memo m WHERE "
            + "m.userNovel.user.userId = ?1")
    long countByUserId(Long userId);

    @Query(value = "SELECT m FROM Memo m WHERE m.userNovel.userNovelId = ?1")
    List<Memo> findByUserNovelId(Long userNovelId);
}
