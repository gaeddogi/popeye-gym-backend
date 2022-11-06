package com.hy.popeyegym.repository.pt;

import com.hy.popeyegym.domain.pt.Pt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PtRepository extends JpaRepository<Pt, Long>, PtRepositoryCustom{

    Optional<Pt> findByUserIdAndTrainerId(Long userId, Long trainerId);

}
