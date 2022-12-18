package com.hy.popeyegym.repository.enroll;

import java.util.List;

import static com.hy.popeyegym.dto.response.EnrollResponseDto.GetTrainersRes;

public interface EnrollRepositoryCustom {

    List<GetTrainersRes> getTrainers(Long userId);
}
