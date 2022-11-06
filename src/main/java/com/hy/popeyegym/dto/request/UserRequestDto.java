package com.hy.popeyegym.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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

}
