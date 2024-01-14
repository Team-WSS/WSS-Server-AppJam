package com.wss.websoso.avatar;

import com.wss.websoso.avatar.dto.AvatarGetResponse;
import com.wss.websoso.avatar.dto.UserRepAvatarGetResponse;
import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.avatarLine.AvatarLineRepository;
import com.wss.websoso.user.User;
import com.wss.websoso.user.UserRepository;
import com.wss.websoso.userAvatar.UserAvatar;
import com.wss.websoso.userAvatar.UserAvatarRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvatarService {

    private static final int TOTAL_AVATAR_LINES = 5;
    private final UserAvatarRepository userAvatarRepository;
    private final AvatarLineRepository avatarLineRepository;
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;

    public UserRepAvatarGetResponse getRepAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Long userRepAvatarId = user.getUserRepAvatarId();

        Avatar avatar = avatarRepository.findById(userRepAvatarId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아바타가 없습니다."));

        userAvatarRepository.findByUserAndAvatar(user, avatar)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 해당 아바타를 가지고 있지 않습니다."));

        List<AvatarLine> avatarLines = avatarLineRepository.findByAvatar(avatar);
        String randomAvatarLine = avatarLines.get((int) (System.currentTimeMillis() % TOTAL_AVATAR_LINES))
                .getAvatarLineContent();

        return UserRepAvatarGetResponse.of(avatar, randomAvatarLine, user.getUserNickname());
    }

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

    public AvatarGetResponse getAvatar(Long userId, Long avatarId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아바타가 없습니다."));

        Optional<UserAvatar> userAvatar = userAvatarRepository.findByUserAndAvatar(user, avatar);

        if (userAvatar.isEmpty()) {
            return AvatarGetResponse.of(avatar, avatar.getAvatarUnacquiredMent(),
                    avatar.getAvatarUnacquiredCondition());
        }

        return AvatarGetResponse.of(avatar, avatar.getAvatarAcquiredMent(), avatar.getAvatarAcquiredCondition());
    }
}