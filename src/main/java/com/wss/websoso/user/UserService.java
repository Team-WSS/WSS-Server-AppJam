package com.wss.websoso.user;

import com.wss.websoso.config.jwt.JwtProvider;
import com.wss.websoso.config.jwt.UserAuthentication;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public String login(String userNickname) throws AuthException {
        User user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new AuthException("해당하는 사용자가 없습니다."));

        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return jwtProvider.generateToken(userAuthentication);
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
}