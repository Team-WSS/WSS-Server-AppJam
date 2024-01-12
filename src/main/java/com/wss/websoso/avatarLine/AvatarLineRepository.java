package com.wss.websoso.avatarLine;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvatarLineRepository extends JpaRepository<AvatarLine, Long> {

    @Query(value = "SELECT al FROM AvatarLine al WHERE al.avatar.avatarId = ?1")
    List<AvatarLine> findByAvatarId(Long avatarId);
}
