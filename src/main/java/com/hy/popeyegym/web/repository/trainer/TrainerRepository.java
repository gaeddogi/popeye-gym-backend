package com.hy.popeyegym.web.repository.trainer;

import com.hy.popeyegym.web.domain.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long>, TrainerRepositoryCustom {


}
