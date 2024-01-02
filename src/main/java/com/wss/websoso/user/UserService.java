package com.wss.websoso.user;

import com.wss.websoso.config.jwt.JwtTokenProvider;
import com.wss.websoso.config.jwt.UserAuthentication;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public String login(String userNickname) throws AuthException {
        User user = userRepository.findByUserNickname(userNickname)
                .orElseThrow(() -> new AuthException("해당하는 사용자가 없습니다."));


        UserAuthentication userAuthentication = new UserAuthentication(user.getUserId(), null, null);

        return jwtTokenProvider.generateToken(userAuthentication);
    }
}