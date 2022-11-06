package com.hy.popeyegym.config;

import com.hy.popeyegym.domain.user.Role;
import com.hy.popeyegym.security.filter.JwtExceptionFilter;
import com.hy.popeyegym.security.handler.OAuth2AuthenticationSuccessHandler;
import com.hy.popeyegym.security.handler.OAuth2LogoutHandler;
import com.hy.popeyegym.security.handler.RestAuthenticationEntryPoint;
import com.hy.popeyegym.security.repository.OAuth2AuthorizationRequestBaseOnCookieRepository;
import com.hy.popeyegym.security.service.PrincipalOAuth2UserService;
import com.hy.popeyegym.security.filter.TokenAuthenticationFilter;
import com.hy.popeyegym.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.ForceEagerSessionCreationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final OAuth2AuthorizationRequestBaseOnCookieRepository authorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final OAuth2LogoutHandler auth2LogoutHandler;
    private final RedisTemplate redisTemplate;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint()) // 인증되지 않은 사용자의 접근 처리
//                .accessDeniedHandler(tokenAccessDeniedHandler) // 인가되지 않은 사용자의 접근 처리

//                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/**").hasAnyAuthority(Role.ROLE_USER.name())

                .anyRequest().permitAll()

                //oauth2 설정
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization") // provider 로그인 폼으로 가는 uri
                .authorizationRequestRepository(authorizationRequestRepository)

                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*") // 구글 로그인 인증 후 리다이렉트 되는 내 서버 uri

                .and()
                .userInfoEndpoint()
                .userService(principalOAuth2UserService) // 받은 사용자 정보를 후처리 하는 곳

                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)  // 인증 후 서비스까지 간 후 successHandler 호출
//                .failureHandler(oAuth2AuthenticationFailureHandler()) // 구글회원 인증 실패 시 리다이렉트 후 error 발생 -> failureHandler 호출

                .and()
                .logout()
                .logoutUrl("/api/v1/logout")
                .permitAll()
                .addLogoutHandler(auth2LogoutHandler)
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                })

                .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, ForceEagerSessionCreationFilter.class);
        return http.build();
    }


    /**
     * 토큰 필터
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(jwtTokenProvider, principalOAuth2UserService, redisTemplate);
    }

    /**
     * OAuth2 인증 성공 핸들러
     */
//    @Bean
//    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
//        return new OAuth2AuthenticationSuccessHandler(jwtTokenProvider, authorizationRequestRepository);
//    }
}
