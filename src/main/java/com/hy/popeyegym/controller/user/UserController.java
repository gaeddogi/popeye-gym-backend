package com.hy.popeyegym.controller.user;

import com.hy.popeyegym.domain.user.User;
import com.hy.popeyegym.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hy.popeyegym.dto.response.UserResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("")
    public UserInfoRes getUserInfo(
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        System.out.println("유저 정보 내놔");
        User userInfo = user.getUser();
        return UserInfoRes.builder()
                .id(userInfo.getId())
                .role(userInfo.getRole())
                .build();
    }
}
