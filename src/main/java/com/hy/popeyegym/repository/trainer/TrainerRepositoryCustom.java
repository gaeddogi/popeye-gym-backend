package com.hy.popeyegym.repository.trainer;

import com.hy.popeyegym.domain.trainer.TrainerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.hy.popeyegym.dto.response.TrainerResponseDto.GetTrainerAllRes;

public interface TrainerRepositoryCustom {
    Page<GetTrainerAllRes> getTrainerAllRes(String nameParam, TrainerType type, Pageable pageable);
}
