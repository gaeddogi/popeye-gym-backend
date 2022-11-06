package com.hy.popeyegym.service.trainer;

import com.hy.popeyegym.repository.trainer.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public void getTrainers() {
//        trainerRepository.
    }

}
