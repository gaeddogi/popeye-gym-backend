package com.hy.popeyegym.repository.trainer;

import com.hy.popeyegym.domain.trainer.TrainerType;
import com.hy.popeyegym.dto.response.TrainerResponseDto;

import java.util.List;

import static com.hy.popeyegym.dto.response.TrainerResponseDto.*;

public interface TrainerRepositoryCustom {
    List<GetTrainerAllRes> getTrainerAllRes(String nameParam, TrainerType type);
}
