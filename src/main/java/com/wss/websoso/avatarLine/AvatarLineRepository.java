package com.wss.websoso.avatarLine;

import com.wss.websoso.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarLineRepository extends JpaRepository<AvatarLine, Long> {

    List<AvatarLine> findByAvatar(Avatar avatar);
}
