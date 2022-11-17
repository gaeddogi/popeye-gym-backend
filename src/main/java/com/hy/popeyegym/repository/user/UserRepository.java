package com.hy.popeyegym.repository.user;

import com.hy.popeyegym.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
