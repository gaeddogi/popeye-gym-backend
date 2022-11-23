package com.hy.popeyegym.service.pt;

import com.hy.popeyegym.domain.pt.QPt;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.dto.response.PtResponseDto;
import com.hy.popeyegym.repository.pt.PtRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.hy.popeyegym.domain.pt.QPt.*;
import static com.hy.popeyegym.domain.trainer.QTrainer.trainer;
import static com.hy.popeyegym.dto.response.PtResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PtService {

    private final PtRepository ptRepository;

    public List<GetUserPtInfoRes> getUserPtInfo(Long id) {
        return ptRepository.getPtTrainers(id);
    }
}
