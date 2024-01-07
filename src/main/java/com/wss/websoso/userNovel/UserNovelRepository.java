package com.wss.websoso.userNovel;

import com.wss.websoso.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNovelRepository extends JpaRepository<UserNovel, Long> {
    Optional<UserNovel> findByUserAndNovelId(User user, Long novelId);
}
