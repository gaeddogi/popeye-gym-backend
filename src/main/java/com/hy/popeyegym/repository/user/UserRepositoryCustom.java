package com.hy.popeyegym.repository.user;

import com.hy.popeyegym.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hy.popeyegym.dto.response.UserResponseDto.*;


public interface UserRepositoryCustom {

    Page<GetUserAllRes> getUserAll(String email, Pageable pageable);
}
