package com.wss.websoso.userAvatar;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {

    @Query(value = "SELECT ua.avatar.avatarId FROM UserAvatar ua WHERE ua.user.userId = ?1")
    List<Long> findAvatarIdByUserId(Long userId);

    Optional<UserAvatar> findByUserAndAvatar(User user, Avatar avatar);
}
