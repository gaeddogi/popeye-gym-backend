package com.hy.popeyegym.repository.user;

import com.hy.popeyegym.domain.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    UserRefreshToken findByUserId(Long userId);
    UserRefreshToken findByUserIdAndRefreshToken(Long userId, String refreshToken);
    void deleteByUserId(Long userId);
}
