package com.wss.websoso.userAvatar;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {

    Optional<UserAvatar> findByUserAndAvatar(User user, Avatar avatar);
}
