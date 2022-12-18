package com.hy.popeyegym.web.repository.user;

import com.hy.popeyegym.web.domain.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    UserRefreshToken findByUserId(Long userId);
    UserRefreshToken findByUserIdAndRefreshToken(Long userId, String refreshToken);
    void deleteByUserId(Long userId);
}
