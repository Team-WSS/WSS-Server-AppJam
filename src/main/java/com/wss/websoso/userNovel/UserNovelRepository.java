package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNovelRepository extends JpaRepository<UserNovel, Long> {

    // ALL, NEWEST
    @Query(value = "SELECT un FROM UserNovel un WHERE "
            + "un.userNovelId < ?1 "
            + "ORDER BY un.userNovelId DESC")
    Slice<UserNovel> findUserNovelsByNewest(Long lastUserNovelId, PageRequest pageRequest);

    // ALL, OLDEST
    @Query(value = "SELECT un FROM UserNovel un WHERE "
            + "un.userNovelId > ?1")
    Slice<UserNovel> findUserNovelsByOldest(Long lastUserNovelId, PageRequest pageRequest);

    // [FINISH, READING, DROP, WISH], NEWEST
    @Query(value = "SELECT un FROM UserNovel un "
            + "WHERE un.userNovelReadStatus = ?1 AND un.userNovelId < ?2 "
            + "ORDER BY un.userNovelId DESC")
    Slice<UserNovel> findUserNovelsByReadStatusAndNewest(ReadStatus readStatus, Long lastUserNovelId,
                                                         PageRequest pageRequest);

    // [FINISH, READING, DROP, WISH], OLDEST
    @Query(value = "SELECT un FROM UserNovel un "
            + "WHERE un.userNovelReadStatus = ?1 AND un.userNovelId > ?2")
    Slice<UserNovel> findUserNovelsByReadStatusAndOldest(ReadStatus readStatus, Long lastUserNovelId,
                                                         PageRequest pageRequest);

    long count();

    Long countByUserNovelReadStatus(ReadStatus readStatus);
}