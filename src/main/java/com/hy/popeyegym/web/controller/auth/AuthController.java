package com.hy.popeyegym.web.controller.auth;

import com.hy.popeyegym.security.util.HeaderUtils;
import com.hy.popeyegym.web.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.hy.popeyegym.web.dto.response.AuthResponseDto.ReissueRes;

@Api(tags = "인증")
@Tag(name = "인증",  description = "인증 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "토큰 재발급", description = "유효한 리프레시 토큰을 받아 엑세스 토큰을 재발급 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!", content = @Content(schema = @Schema(implementation = ReissueRes.class))),
    })
    @PostMapping("reissue")
    public ReissueRes reissue(
            HttpServletRequest request,
            @CookieValue("refresh_token") @Parameter(description = "리프레시 토큰", required = true) String refreshToken
    ) {
        String accessToken = HeaderUtils.getAccessToken(request);
        String newAccessToken = authService.reissue(accessToken, refreshToken);

        return ReissueRes.builder().token(newAccessToken).build();
    }

}
