package com.hy.popeyegym.security.handler;

import com.hy.popeyegym.web.repository.user.UserRefreshTokenRepository;
import com.hy.popeyegym.security.util.HeaderUtils;
import com.hy.popeyegym.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler extends SecurityContextLogoutHandler{

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        super.logout(request, response, authentication);
        log.info("logout");

        String accessToken = HeaderUtils.getAccessToken(request);
        // 1. accessToken 검증 -> success: true, fail: exception 발생
        jwtTokenProvider.validateToken(accessToken);

        // 2. redis에 accessToken 저장
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue()
                .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        // 3. db refreshToken 삭제
        Long userId = jwtTokenProvider.getIdFromToken(accessToken);
        userRefreshTokenRepository.deleteByUserId(userId);

        // 4. 쿠키에서 RT 지움.., Id, AT도 들어갔음. 근데

    }
}
