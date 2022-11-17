package com.hy.popeyegym.repository.user;

import com.hy.popeyegym.dto.response.UserResponseDto;

import java.util.List;

import static com.hy.popeyegym.dto.response.UserResponseDto.*;

public interface UserRepositoryCustom {

    List<GetUserAllRes> getUserAll(String emailParam);
}
