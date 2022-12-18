package com.hy.popeyegym.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.hy.popeyegym.dto.response.UserResponseDto.GetUserAllRes;


public interface UserRepositoryCustom {

    Page<GetUserAllRes> getUserAll(String email, Pageable pageable);
}
