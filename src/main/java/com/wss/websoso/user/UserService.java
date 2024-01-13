package com.wss.websoso.user;

import com.wss.websoso.config.jwt.JwtProvider;
import com.wss.websoso.config.jwt.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public UserLoginRequest login(String userNickname) {
        User user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new RuntimeException("해당하는 사용자가 없습니다."));

        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return UserLoginRequest.of(jwtProvider.generateToken(userAuthentication));
    }
}