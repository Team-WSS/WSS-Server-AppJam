package com.wss.websoso.avatar;

import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userAvatar.UserAvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {

    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    @Transactional
    public void updateUserRepAvatar(Long userId, Long avatarId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아바타가 없습니다."));

        userAvatarRepository.findByUserAndAvatar(user, avatar)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 해당 아바타를 보유하고 있지 않습니다."));

        if (user.getUserRepAvatarId() == avatarId) {
            throw new IllegalArgumentException("이미 대표 아바타로 설정된 아바타입니다.");
        }

        user.updateUserRepAvatar(avatarId);
    }
}