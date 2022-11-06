package com.hy.popeyegym.repository.enroll;

import com.hy.popeyegym.domain.enroll.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {
}
