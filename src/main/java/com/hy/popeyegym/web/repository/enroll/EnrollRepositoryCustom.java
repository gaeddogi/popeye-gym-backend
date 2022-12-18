package com.hy.popeyegym.web.repository.enroll;

import java.util.List;

import static com.hy.popeyegym.web.dto.response.EnrollResponseDto.GetTrainersRes;

public interface EnrollRepositoryCustom {

    List<GetTrainersRes> getTrainers(Long userId);
}
