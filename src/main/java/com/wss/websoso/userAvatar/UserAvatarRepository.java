package com.wss.websoso.userAvatar;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {

    @Query(value = "SELECT ua.avatar.avatarId FROM UserAvatar ua WHERE ua.user.userId = ?1")
    List<Long> findAvatarIdByUserId(Long userId);

    Optional<UserAvatar> findByUserAndAvatar(User user, Avatar avatar);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_avatar (user_id, avatar_id) VALUES (?1 , ?2)", nativeQuery = true)
    void createUserAvatar(Long userId, Long avatarId);
}
