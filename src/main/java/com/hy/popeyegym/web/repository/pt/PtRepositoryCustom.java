package com.hy.popeyegym.web.repository.pt;

import java.util.List;

import static com.hy.popeyegym.web.dto.response.PtResponseDto.GetUserPtInfoRes;

public interface PtRepositoryCustom {

    List<GetUserPtInfoRes> getPtTrainers(Long userId);
}
