package com.hy.popeyegym.web.service.user;

import com.hy.popeyegym.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hy.popeyegym.web.dto.response.UserResponseDto.GetUserAllRes;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public Page<GetUserAllRes> getUserAll(String emailParam, Pageable pageable) {
        return userRepository.getUserAll(emailParam, pageable);
    }
}
