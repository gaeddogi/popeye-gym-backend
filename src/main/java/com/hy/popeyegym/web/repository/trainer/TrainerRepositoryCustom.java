package com.hy.popeyegym.web.repository.trainer;

import com.hy.popeyegym.web.domain.trainer.TrainerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.hy.popeyegym.web.dto.response.TrainerResponseDto.GetTrainerAllRes;

public interface TrainerRepositoryCustom {
    Page<GetTrainerAllRes> getTrainerAllRes(String nameParam, TrainerType type, Pageable pageable);
}
