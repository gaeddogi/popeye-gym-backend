package com.hy.popeyegym.security.handler;

import com.hy.popeyegym.web.domain.user.UserRefreshToken;
import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.AuthExceptionType;
import com.hy.popeyegym.web.repository.user.UserRefreshTokenRepository;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.security.repository.OAuth2AuthorizationRequestBaseOnCookieRepository;
import com.hy.popeyegym.security.util.CookieUtils;
import com.hy.popeyegym.security.token.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.hy.popeyegym.security.repository.OAuth2AuthorizationRequestBaseOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AuthorizationRequestBaseOnCookieRepository authorizationRequestRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final String REDIRECT_URI;
    private final long REFRESH_TOKEN_EXPIRE_TIME;
    private final static String REFRESH_TOKEN = "refresh_token";


    public OAuth2AuthenticationSuccessHandler(
            JwtTokenProvider jwtTokenProvider,
            OAuth2AuthorizationRequestBaseOnCookieRepository authorizationRequestRepository,
            UserRefreshTokenRepository userRefreshTokenRepository,
            @Value("${spring.oauth2.redirect_uri}") String redirectUri,
           @Value("${spring.jwt.refresh_token_expire_time}") long refreshTime
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
        this.REDIRECT_URI = redirectUri;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
    }


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        log.info("OAuth2AuthenticationSuccessHandler: {}", request.getQueryString());

        // 쿠키로부터 targetUrl 가져오기
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("targetUrl: {}", targetUrl);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        // redirectUri가 유효하지 않으면 exception 던짐.
        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            System.out.println("redirectUri가 유효하지 않음");
            throw new CustomException(AuthExceptionType.AUTH_EXCEPTION);
//            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        // Access Token 발급
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        Long userId = principal.getUser().getId();
        String role = principal.getUser().getRole().name();
        String accessToken = jwtTokenProvider.createToken(userId, role);

        // Refresh Token 발급
        String refreshToken = jwtTokenProvider.createToken();
        log.info("refresh token: {}", refreshToken);

        // Refresh Token db 저장
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken != null) {

            userRefreshToken.updateRefreshToken(refreshToken);
        } else {
            userRefreshToken = UserRefreshToken.builder()
                    .userId(userId)
                    .refreshToken(refreshToken)
                    .build();
        }
        userRefreshTokenRepository.saveAndFlush(userRefreshToken); // 여기서 saveAndFlush....?

        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN, refreshToken, (int)REFRESH_TOKEN_EXPIRE_TIME / 60); // 쿠키 만료시간 정하기

        return UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("id", principal.getUser().getId())
                .queryParam("token", accessToken)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizeRedirectUri = URI.create(REDIRECT_URI);

        System.out.println("uri " + clientRedirectUri.getHost() + " " + authorizeRedirectUri.getHost());

        return authorizeRedirectUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost());
//        return appProperties.getOauth2().getAuthorizedRedirectUris()
//                .stream()
//                .anyMatch(authorizedRedirectUri -> {
//                    // Only validate host and port. Let the clients use different paths if they want to
//                    URI authorizedURI = URI.create(authorizedRedirectUri);
//                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//                        return true;
//                    }
//                    return false;
//                });
    }

}
