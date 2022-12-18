package com.hy.popeyegym.repository.trainer;

import com.hy.popeyegym.domain.trainer.TrainerType;
import com.hy.popeyegym.dto.response.TrainerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hy.popeyegym.dto.response.TrainerResponseDto.*;

public interface TrainerRepositoryCustom {
    Page<GetTrainerAllRes> getTrainerAllRes(String nameParam, TrainerType type, Pageable pageable);
}
