package com.hy.popeyegym.web.controller.auth;

import com.hy.popeyegym.security.util.HeaderUtils;
import com.hy.popeyegym.web.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.hy.popeyegym.web.dto.response.AuthResponseDto.ReissueRes;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("reissue")
    public ReissueRes reissue(
            HttpServletRequest request,
            @CookieValue("refresh_token") String refreshToken
    ) {
        String accessToken = HeaderUtils.getAccessToken(request);
        String newAccessToken = authService.reissue(accessToken, refreshToken);

        return ReissueRes.builder().token(newAccessToken).build();
    }

}
