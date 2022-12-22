package com.hy.popeyegym.web.controller.user;

import com.hy.popeyegym.web.domain.user.User;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.web.dto.response.ReservationResponseDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hy.popeyegym.web.dto.response.UserResponseDto.UserInfoRes;

@Api(tags = "유저")
@Tag(name = "유저",  description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("")
    @Operation(summary = "유저 권한", description = "유저 권한 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!", content = @Content(schema = @Schema(implementation = ReservationResponseDto.ReservationRes.class))),
    })
    public UserInfoRes getUserInfo(
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        User userInfo = user.getUser();
        return UserInfoRes.builder()
                .id(userInfo.getId())
                .role(userInfo.getRole())
                .build();
    }
}
