package com.hy.popeyegym.web.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

public class UserRequestDto {

    @Data
    public static class UserSignUpRequestDto {

        @NotBlank(message = "이메일은 필수 입력값입니다.")
//        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
//        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;
    }

    @Data
    public static class UserLoginRequestDto {

        @NotBlank(message = "이메일을 입력하세요.")
        private String email;
        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;

    }

    @Data
    public static class UserSearchReq {

        private String emailParam;
        private int page;
    }

    // int currentPage; 사용자가 요청한 값 -> 1
    // int totalCount; db에서 가져옴 ->  100개
    // int limit; 한 페이지 당 10개

    // int startPage;  Math.floor(cp-1 / barSize)  * barSize + 1
    // int endPage; startPage -1 + barSize
    // int barSize;

    // int first; 무조건 1
    // int last; Math.ceil(totalCount / limit)
}
