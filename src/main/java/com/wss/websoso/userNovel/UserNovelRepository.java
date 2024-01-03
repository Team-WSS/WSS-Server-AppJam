package com.wss.websoso.userNovel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNovelRepository extends JpaRepository<UserNovel, Long> {
}
