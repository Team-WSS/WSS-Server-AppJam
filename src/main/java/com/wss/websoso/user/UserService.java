package com.wss.websoso.user;

import com.wss.websoso.avatar.Avatar;
import com.wss.websoso.avatar.AvatarRepository;
import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.avatarLine.AvatarLineRepository;
import com.wss.websoso.config.jwt.JwtProvider;
import com.wss.websoso.config.jwt.UserAuthentication;
import com.wss.websoso.user.dto.UserInfoGetResponse;
import com.wss.websoso.user.dto.UserLoginRequest;
import com.wss.websoso.userAvatar.UserAvatarRepository;
import com.wss.websoso.userNovel.UserNovelRepository;
import com.wss.websoso.userNovel.dto.UserAvatarsGetResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    public static final int TOTAL_AVATAR_LINES = 5;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserNovelRepository userNovelRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarLineRepository avatarLineRepository;
    private final UserAvatarRepository userAvatarRepository;

    public UserLoginRequest login(String userNickname) {
        User user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new RuntimeException("해당하는 사용자가 없습니다."));

        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return UserLoginRequest.of(jwtProvider.generateToken(userAuthentication));
    }

    @Transactional
    public void updateNickname(Long userId, String newUserNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        if (Objects.equals(newUserNickname, user.getUserNickname())) {
            throw new IllegalArgumentException("이전 닉네임과 동일합니다.");
        }

        if (newUserNickname == null || newUserNickname.isBlank()) {
            throw new IllegalArgumentException("닉네임이 없습니다.");
        }

        user.updateUserNickname(newUserNickname);
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
        List<UserAvatarsGetResponse> userAvatarList = new ArrayList<>(allAvatars.stream()
                .map(eachAvatar -> new UserAvatarsGetResponse(eachAvatar.getAvatarId(),
                        ownAvatarIdList.contains(eachAvatar.getAvatarId()) ?
                                eachAvatar.getAvatarAcquiredImg() : eachAvatar.getAvatarUnacquiredImg())).toList());

        return UserInfoGetResponse.of(user, avatar, userNovelCount,
                avatarLines.get((int) (System.currentTimeMillis() % TOTAL_AVATAR_LINES)), memoCount, userAvatarList);
    }
}