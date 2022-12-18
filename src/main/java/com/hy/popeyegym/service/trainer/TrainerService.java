package com.hy.popeyegym.service.trainer;

import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.dto.request.TrainerRequestDto;
import com.hy.popeyegym.dto.response.TrainerResponseDto;
import com.hy.popeyegym.repository.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hy.popeyegym.dto.request.TrainerRequestDto.*;
import static com.hy.popeyegym.dto.response.TrainerResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public Page<GetTrainerAllRes> getTrainerAll(GetTrainerAllReq req, Pageable pageable) {
        return trainerRepository.getTrainerAllRes(req.getNameParam(), req.getType(), pageable);
    }

    @Transactional
    public Long enroll(EnrollReq req) {
        Trainer trainer = Trainer.create(req.getName(), req.getType());
        trainerRepository.save(trainer);

        return trainer.getId();
    }
}
