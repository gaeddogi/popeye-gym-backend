package com.hy.popeyegym.repository.enroll;

import com.hy.popeyegym.dto.response.EnrollResponseDto;

import java.util.List;

import static com.hy.popeyegym.dto.response.EnrollResponseDto.*;

public interface EnrollRepositoryCustom {

    List<GetTrainersRes> getTrainers(Long userId);
}
