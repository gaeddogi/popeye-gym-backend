package com.hy.popeyegym.admin.controller.user;

import com.hy.popeyegym.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hy.popeyegym.dto.response.UserResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class UserControllerForAdmin {

    private final UserService userService;

    @GetMapping("users")
    public List<GetUserAllRes> getUserAll(
            String emailParam
    ) {
        return userService.getUserAll(emailParam);
    }
}
