package com.hy.popeyegym.web.admin.controller.user;

import com.hy.popeyegym.web.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hy.popeyegym.web.dto.response.UserResponseDto.GetUserAllRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class UserControllerForAdmin {

    private final UserService userService;

    @GetMapping("users")
    public Page<GetUserAllRes> getUserAll(
            String emailParam,
            @PageableDefault(sort = {"email"}, direction = Sort.Direction.ASC, size = 7) Pageable pageable
    ) {
        return userService.getUserAll(emailParam, pageable);
    }
}
