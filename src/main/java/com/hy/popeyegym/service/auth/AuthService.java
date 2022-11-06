package com.hy.popeyegym.service.auth;

import com.hy.popeyegym.domain.user.UserRefreshToken;
import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.JwtExceptionType;
import com.hy.popeyegym.repository.user.UserRefreshTokenRepository;
import com.hy.popeyegym.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String reissue(String accessToken, String refreshToken) {

        Long userId = jwtTokenProvider.getIdFromToken(accessToken);
        String role = jwtTokenProvider.getRoleFromToken(accessToken);
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId); // queryDsl 적용해서 refreshToken만 가져오게 수정하자

        if (userRefreshToken.getRefreshToken().equals(refreshToken)) {
            try {
                jwtTokenProvider.validateToken(refreshToken);
            } catch (CustomException e) {
                if (e.getExceptionType() == JwtExceptionType.INVALID_TOKEN) {
                    throw new CustomException(JwtExceptionType.REFRESH_TOKEN_EXPIRED);
                }
                throw e;
            }

            String newAccessToken = jwtTokenProvider.createToken(userId, role);

            return newAccessToken;

        } else {
            throw new CustomException(JwtExceptionType.INVALID_TOKEN);
        }

    }
}
