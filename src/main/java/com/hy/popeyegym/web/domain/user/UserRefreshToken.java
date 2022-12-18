package com.hy.popeyegym.web.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
//@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_refresh_token_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    private String refreshToken;

    @Builder
    public UserRefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    /**
     * 비지니스 로직
     **/
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
