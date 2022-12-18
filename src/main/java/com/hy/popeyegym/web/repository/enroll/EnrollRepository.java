package com.hy.popeyegym.web.repository.enroll;

import com.hy.popeyegym.web.domain.enroll.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollRepository extends JpaRepository<Enroll, Long>, EnrollRepositoryCustom {

}
