package com.hy.popeyegym.controller.auth;

import com.hy.popeyegym.dto.response.AuthResponseDto;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.security.util.HeaderUtils;
import com.hy.popeyegym.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.hy.popeyegym.dto.response.AuthResponseDto.*;

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
