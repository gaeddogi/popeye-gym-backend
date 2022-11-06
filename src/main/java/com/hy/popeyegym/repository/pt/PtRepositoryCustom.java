package com.hy.popeyegym.repository.pt;

import com.hy.popeyegym.domain.trainer.Trainer;
import com.querydsl.core.Tuple;

import java.util.List;

public interface PtRepositoryCustom {

    List<Tuple> getPtTrainers(Long userId);
}
