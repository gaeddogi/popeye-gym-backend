package com.hy.popeyegym.service.reservation;

import com.hy.popeyegym.domain.pt.Pt;
import com.hy.popeyegym.domain.reservation.Reservation;
import com.hy.popeyegym.domain.trainer.Trainer;
import com.hy.popeyegym.domain.user.User;
import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.ReservationType;
import com.hy.popeyegym.exception.exceptionType.TrainerExceptionType;
import com.hy.popeyegym.exception.exceptionType.UserExceptionType;
import com.hy.popeyegym.repository.pt.PtRepository;
import com.hy.popeyegym.repository.reservation.ReservationRepository;
import com.hy.popeyegym.repository.trainer.TrainerRepository;
import com.hy.popeyegym.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hy.popeyegym.dto.request.ReservationRequestDto.*;
import static com.hy.popeyegym.dto.response.ReservationResponseDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final PtRepository ptRepository;

    /**
     * pt 예약
     */
    @Transactional
    public Long reservation(Long userId, ReservationReq req) {

        Long trainerId = req.getTrainerId();
        LocalDateTime dateTime = req.getDateTime();

        // 엔티티 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserExceptionType.NOT_FOUND_USER));
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new CustomException(TrainerExceptionType.NOT_FOUND_TRAINER));

        // 해당 날짜에 대한 트레이너 스케줄이 비어있는지 확인
        boolean present = reservationRepository.findByTrainerIdAndDateTime(trainerId, dateTime).isPresent();
        if (present) throw new CustomException(ReservationType.ALREADY_RESERVED);

        // pt권 확인
        Pt pt = ptRepository.findByUserIdAndTrainerId(userId, trainerId)
                .orElseThrow(() -> new CustomException(ReservationType.NOT_ENROLL_PT));

        boolean isRemaining = pt.minus();
        if (!isRemaining) {
            throw new CustomException(ReservationType.NOT_REMAINING);
        }

        // 예약 생성
        Reservation reservation = Reservation.create(user, trainer, dateTime);

        // 예약 저장
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    /**
     * 예약 취소
     */
    @Transactional
    public void cancel(ReserCancelRequestDto dto) {
        // 예약이 있는지 확인
        // 일단 단건 취소만 가능
//        List<Reservation> reservations = new ArrayList<>();
//        for (Long reservationId : reservationIds) {
//            Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, userId)
//                    .orElseThrow(() -> new CustomException(ReservationErrorType.NOT_CANCEL));
//            reservations.add(reservation);
//        }
        Reservation reservation = reservationRepository.findByIdAndUserId(dto.getReservationId(), dto.getUserId())
                .orElseThrow(() -> new CustomException(ReservationType.NOT_CANCEL));

        Pt pt = ptRepository.findByUserIdAndTrainerId(dto.getUserId(), reservation.getTrainer().getId())
                .orElseThrow(() -> new CustomException(ReservationType.NOT_ENROLL_PT));

        // 예약상태 reservation -> cancel
        // completed나 in_process나 cancel인 것은 넘어오면 안되는데, 넘어오더라도 그것은 무시한다.
        reservation.cancel();

        // pt권 quantity +1
        pt.cancel();
    }

    /**
     * 트레이너의 예약 스케줄을 가져온다
     */
//    public GetScheduleOfTrainerRes getScheduleOfTrainer(Long userId, Long trainerId, getScheduleOfTrainerReq req) {
//
//        LocalDate today = LocalDate.now();
//        LocalDate startDt = req.getStartDt();
//        if (today.isAfter(startDt)) startDt = today;
//
//        List<Reservation> scheduleOfTrainer =
//                reservationRepository.findScheduleOfTrainer(trainerId, startDt, req.getEndDt());
//
//        List<ScheduleDetails> scheduleDetails =
//                scheduleOfTrainer.stream()
//                .map(o -> {
//                    return ScheduleDetails.builder()
//                            .date(o.getTrainingDt())
//                            .time(o.getStartTime())
//                            .isMine(o.getUser().getId() == userId ? true: false)
//                            .build();
//                })
//                .collect(Collectors.toList());
//
//        return GetScheduleOfTrainerRes.builder()
//                .trainerId(trainerId)
//                .scheduleDetails(scheduleDetails)
//                .build();
//    }




}
