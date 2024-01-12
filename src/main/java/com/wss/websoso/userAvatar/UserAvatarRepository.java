package com.wss.websoso.userAvatar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {

    @Query(value = "SELECT ua.avatar.avatarId FROM UserAvatar ua WHERE ua.user.userId = ?1")
    List<Long> findAvatarIdByUserId(Long userId);
}
