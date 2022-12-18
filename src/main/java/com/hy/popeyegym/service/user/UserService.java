package com.hy.popeyegym.service.user;

import com.hy.popeyegym.dto.request.UserRequestDto;
import com.hy.popeyegym.dto.response.UserResponseDto;
import com.hy.popeyegym.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hy.popeyegym.dto.request.UserRequestDto.*;
import static com.hy.popeyegym.dto.response.UserResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public Page<GetUserAllRes> getUserAll(String emailParam, Pageable pageable) {
        return userRepository.getUserAll(emailParam, pageable);
    }
}
