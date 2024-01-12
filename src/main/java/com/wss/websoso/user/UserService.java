package com.wss.websoso.user;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.avatar.AvatarRepository;
import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.avatarLine.AvatarLineRepository;
import com.wss.websoso.config.jwt.JwtProvider;
import com.wss.websoso.config.jwt.UserAuthentication;
import com.wss.websoso.userAvatar.UserAvatarRepository;
import com.wss.websoso.userNovel.UserAvatarsGetResponse;
import com.wss.websoso.userNovel.UserNovelRepository;
import jakarta.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    public static final int TOTAL_AVATAR_LINES = 10;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserNovelRepository userNovelRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarLineRepository avatarLineRepository;
    private final UserAvatarRepository userAvatarRepository;

    public String login(String userNickname) throws AuthException {
        User user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new AuthException("해당하는 사용자가 없습니다."));

        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return jwtProvider.generateToken(userAuthentication);
    }

    public UserInfoGetResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당하는 사용자가 없습니다."));

        Long userRepresentativeAvatarId = user.getUserRepAvatarId();
        Avatar avatar = avatarRepository.findById(userRepresentativeAvatarId)
                .orElseThrow(() -> new RuntimeException("해당하는 대표 아바타가 없습니다."));

        List<AvatarLine> avatarLines = avatarLineRepository.findByAvatarId(userRepresentativeAvatarId);

        long userNovelCount = userNovelRepository.countByUserId(userId);

        long memoCount = user.getMemoCount();

        List<Avatar> allAvatars = avatarRepository.findAll();
        List<Long> ownAvatarIdList = userAvatarRepository.findAvatarIdByUserId(userId);
        List<UserAvatarsGetResponse> userAvatarList = new ArrayList<>();
        for (Avatar a : allAvatars) {
            if (ownAvatarIdList.contains(a.getAvatarId())) {
                userAvatarList.add(
                        new UserAvatarsGetResponse(a.getAvatarId(), a.getAvatarAcquiredImg()));
            } else {
                userAvatarList.add(
                        new UserAvatarsGetResponse(a.getAvatarId(), a.getAvatarUnacquiredImg()));
            }
        }

        return UserInfoGetResponse.of(user, avatar, userNovelCount,
                avatarLines.get((int) (System.currentTimeMillis() % TOTAL_AVATAR_LINES)), memoCount, userAvatarList);
    }
}