package com.hy.popeyegym.security.filter;

import com.hy.popeyegym.domain.user.User;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.security.service.PrincipalOAuth2UserService;
import com.hy.popeyegym.security.util.HeaderUtils;
import com.hy.popeyegym.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

//        User user = null;
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            user = principal.getUser();
//
//        }
//
//        log.info("user: {}", user);
        log.info("request uri: {}", request.getRequestURI());
//        log.info("isSame: {}", request.getRequestURI().equals("/api/v1/auth/reissue"));

        String token = HeaderUtils.getAccessToken(request);

        // 요청에 토큰이 있으면 SecurityContextHolder에 넣어 로그인 상태로 만들어줌
        if (StringUtils.hasText(token) && !request.getRequestURI().equals("/api/v1/auth/reissue")) {
            log.info("has AccessToken");

            // 1. 토큰 검증
            jwtTokenProvider.validateToken(token);
            // 2. 로그아웃 여부
            String isLogout = (String)redisTemplate.opsForValue().get(token);
            if (!StringUtils.hasText(isLogout)) {
                // 3. 로그인 처리 (SecurityContextHolder에 Autentication객체 넣기)
                Long userId = jwtTokenProvider.getIdFromToken(token);
                UserDetails userDetails = principalOAuth2UserService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 로그인 o -> 요청처리
        // 로그인 x -> 인증 필요하면 디폴트인증엔트리포인트(로그인페이지) or 커스텀 인증엔트리포인트 / 인증 필요 없으면 요청처리
        filterChain.doFilter(request, response);
    }

}
