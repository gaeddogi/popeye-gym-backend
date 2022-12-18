package com.hy.popeyegym.service.enroll;

import com.hy.popeyegym.domain.enroll.Enroll;
import com.hy.popeyegym.domain.pt.Pt;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.User;
import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.TrainerExceptionType;
import com.hy.popeyegym.exception.exceptionType.UserExceptionType;
import com.hy.popeyegym.repository.enroll.EnrollRepository;
import com.hy.popeyegym.repository.pt.PtRepository;
import com.hy.popeyegym.repository.trainer.TrainerRepository;
import com.hy.popeyegym.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hy.popeyegym.dto.request.EnrollRequestDto.EnrollSignUpRequestDto;
import static com.hy.popeyegym.dto.response.EnrollResponseDto.GetTrainersRes;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnrollService {

    private final EnrollRepository enrollRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    private final PtRepository ptRepository;

    /**
     * PT 등록
     */
    @Transactional
    public Long enroll(EnrollSignUpRequestDto dto) {

        // 엔티티 조회
        User user = userRepository.findById(dto.getUserId())
                      .orElseThrow(() -> new CustomException(UserExceptionType.NOT_FOUND_USER));
        Trainer trainer = trainerRepository.findById(dto.getTrainerId())
                            .orElseThrow(() -> new CustomException(TrainerExceptionType.NOT_FOUND_TRAINER));

        // pt권 조회 -> 있으면 수량 업데이트, 없으면 신규Pt 저장
        Optional<Pt> ptOptional = ptRepository.findByUserIdAndTrainerId(user.getId(), trainer.getId());

        if (ptOptional.isPresent()) ptOptional.get().addQuantity(dto.getTimes());
        else ptRepository.save(Pt.create(user, trainer, dto.getTimes()));

        // 등록 생성
        Enroll enroll = Enroll.create(user, trainer, dto.getTimes());
        // 등록 저장
        enrollRepository.save(enroll);

        return enroll.getId();
    }

    public List<GetTrainersRes> getTrainers(Long userId) {
        return enrollRepository.getTrainers(userId);
    }
}
