package com.hy.popeyegym.web.dto.response;

import com.hy.popeyegym.web.domain.user.Role;
import lombok.Builder;
import lombok.Data;

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


//    @Data
//    public static class User {
//        private Long id;
//        private String email;
//    }


}
