package com.hy.popeyegym.repository.pt;

import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.dto.response.PtResponseDto;
import com.querydsl.core.Tuple;

import java.util.List;

import static com.hy.popeyegym.dto.response.PtResponseDto.*;

public interface PtRepositoryCustom {

    List<GetUserPtInfoRes> getPtTrainers(Long userId);
}
