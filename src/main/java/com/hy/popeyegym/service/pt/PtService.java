package com.hy.popeyegym.service.pt;

import com.hy.popeyegym.repository.pt.PtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hy.popeyegym.dto.response.PtResponseDto.GetUserPtInfoRes;

@Service
@RequiredArgsConstructor
@Transactional
public class PtService {

    private final PtRepository ptRepository;

    public List<GetUserPtInfoRes> getUserPtInfo(Long id) {
        return ptRepository.getPtTrainers(id);
    }
}
