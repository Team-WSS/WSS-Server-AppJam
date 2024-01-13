package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.user.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNovelRepository extends JpaRepository<UserNovel, Long> {

    Optional<UserNovel> findByUserAndNovelId(User user, Long novelId);

    // ALL, NEWEST
    @Query(value = "SELECT un FROM UserNovel un WHERE "
            + "un.user.userId = ?1 AND "
            + "un.userNovelId < ?2 "
            + "ORDER BY un.userNovelId DESC")
    Slice<UserNovel> findUserNovelsByNewest(Long userId, Long lastUserNovelId, PageRequest pageRequest);

    // ALL, OLDEST
    @Query(value = "SELECT un FROM UserNovel un WHERE "
            + "un.user.userId = ?1 AND "
            + "un.userNovelId > ?2")
    Slice<UserNovel> findUserNovelsByOldest(Long userId, Long lastUserNovelId, PageRequest pageRequest);

    // [FINISH, READING, DROP, WISH], NEWEST
    @Query(value = "SELECT un FROM UserNovel un WHERE "
            + "un.user.userId = ?1 AND "
            + "un.userNovelReadStatus = ?2 AND un.userNovelId < ?3 "
            + "ORDER BY un.userNovelId DESC")
    Slice<UserNovel> findUserNovelsByReadStatusAndNewest(Long userId, ReadStatus readStatus,
                                                         Long lastUserNovelId, PageRequest pageRequest);

    // [FINISH, READING, DROP, WISH], OLDEST
    @Query(value = "SELECT un FROM UserNovel un WHERE "
            + "un.user.userId = ?1 AND "
            + "un.userNovelReadStatus = ?2 AND un.userNovelId > ?3 ")
    Slice<UserNovel> findUserNovelsByReadStatusAndOldest(Long userId, ReadStatus readStatus,
                                                         Long lastUserNovelId, PageRequest pageRequest);

    @Query(value = "SELECT COUNT(un) FROM UserNovel un WHERE "
            + "un.user.userId = ?1")
    long countByUserId(Long userId);

    @Query(value = "SELECT COUNT(un) FROM UserNovel un WHERE "
            + "un.user.userId = ?1 AND "
            + "un.userNovelReadStatus = ?2")
    Long countByUserNovelReadStatus(Long userId, ReadStatus readStatus);

    UserNovel findByUserNovelId(Long userNovelId);

    @Query(value = "SELECT MAX(un.userNovelId) FROM UserNovel un")
    Optional<Long> findByMaxUserNovelId();

    Long countUserNovelsByNovelId(Long novelId);
}