package com.hy.popeyegym.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password; // 없앨예정임.St
    private String name;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String password, String name, AuthProvider provider, String providerId, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    /** 생성 메서드 */
    public static User create(String email, String password, String name, AuthProvider provider, String providerId, Role role) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .provider(provider)
                .providerId(providerId)
                .role(role)
                .build();
    }

    /** 비지니스 메서드 */

    /**
     * 회원 정보 수정
     */
    public void update(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
