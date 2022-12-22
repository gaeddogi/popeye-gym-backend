package com.hy.popeyegym.web.admin.controller.user;

import com.hy.popeyegym.web.dto.response.EnrollResponseDto;
import com.hy.popeyegym.web.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hy.popeyegym.web.dto.response.UserResponseDto.GetUserAllRes;

@Api(tags = "admin")
@Tag(name = "admin",  description = "admin API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class UserControllerForAdmin {

    private final UserService userService;

    @GetMapping("users")
    @Operation(summary = "모든 회원 목록", description = "검색 조건에 해당하는 모든 회원 목록을 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!", content = @Content(schema = @Schema(implementation = EnrollResponseDto.EnrollSignUpResponseDto.class))),
    })
    public Page<GetUserAllRes> getUserAll(
            String emailParam,
            @PageableDefault(sort = {"email"}, direction = Sort.Direction.ASC, size = 7) Pageable pageable
    ) {
        return userService.getUserAll(emailParam, pageable);
    }
}
