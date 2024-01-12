package com.wss.websoso.avatar;

import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.avatarLine.AvatarLineRepository;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userAvatar.UserAvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private static final int TOTAL_AVATAR_LINES = 10;
    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final AvatarLineRepository avatarLineRepository;

    public UserRepAvatarGetResponse getRepAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Long userRepAvatarId = user.getUserRepAvatarId();

        Avatar avatar = avatarRepository.findById(userRepAvatarId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아바타가 없습니다."));

        userAvatarRepository.findByUserAndAvatar(user, avatar)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 해당 아바타를 가지고 있지 않습니다."));

        List<AvatarLine> avatarLines = avatarLineRepository.findByAvatar(avatar);
        String randomAvatarLine = avatarLines.get((int) (System.currentTimeMillis() % TOTAL_AVATAR_LINES)).getAvatarLineContent();

        return UserRepAvatarGetResponse.of(avatar, randomAvatarLine, user.getUserNickname());
    }
}