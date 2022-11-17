package com.hy.popeyegym.service.user;

import com.hy.popeyegym.dto.response.UserResponseDto;
import com.hy.popeyegym.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hy.popeyegym.dto.response.UserResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    public List<GetUserAllRes> getUserAll(String emailParam) {
        return userRepository.getUserAll(emailParam);
    }
}
