package com.hy.popeyegym.repository.trainer;

import com.hy.popeyegym.domain.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long>, TrainerRepositoryCustom {


}
