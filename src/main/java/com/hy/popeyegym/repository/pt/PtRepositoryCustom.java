package com.hy.popeyegym.repository.pt;

import java.util.List;

import static com.hy.popeyegym.dto.response.PtResponseDto.GetUserPtInfoRes;

public interface PtRepositoryCustom {

    List<GetUserPtInfoRes> getPtTrainers(Long userId);
}
