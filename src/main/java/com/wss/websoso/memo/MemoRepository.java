package com.wss.websoso.memo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Query(value = "SELECT m FROM Memo m WHERE m.userNovel.userNovelId = ?1")
    List<Memo> findByUserNovelId(Long userNovelId);
}