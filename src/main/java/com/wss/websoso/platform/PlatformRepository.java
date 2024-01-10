package com.wss.websoso.platform;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    @Query(value = "SELECT p FROM Platform p WHERE p.userNovel.userNovelId = ?1 ")
    List<Platform> findByUserNovelId(Long userNovelId);
}