package com.wss.websoso.novel;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {
    @Query(value = "SELECT n FROM Novel n WHERE n.novelId < ?1 AND Function('replace', n.novelTitle, ' ', '') LIKE %?2% ORDER BY n.novelId DESC")
    Slice<Novel> findByIdLessThanOrderByIdDesc(Long lastNovelId, PageRequest pageRequest, String word);
}