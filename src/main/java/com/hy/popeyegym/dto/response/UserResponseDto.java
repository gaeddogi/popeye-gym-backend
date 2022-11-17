package com.hy.popeyegym.dto.response;

import com.hy.popeyegym.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

public class UserResponseDto {

    @Data
    @Builder
    public static class UserInfoRes {
        private Long id;
        private Role role;
    }

    @Data
    public static class GetUserAllRes {
        private Long id;
        private String email;
    }


}
