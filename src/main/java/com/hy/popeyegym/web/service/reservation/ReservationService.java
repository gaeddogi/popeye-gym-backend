package com.hy.popeyegym.web.service.reservation;

import com.hy.popeyegym.exception.CustomException;
import com.hy.popeyegym.exception.exceptionType.ReservationType;
import com.hy.popeyegym.exception.exceptionType.TrainerExceptionType;
import com.hy.popeyegym.exception.exceptionType.UserExceptionType;
import com.hy.popeyegym.web.domain.pt.Pt;
import com.hy.popeyegym.web.domain.reservation.Reservation;
import com.hy.popeyegym.web.domain.trainer.Trainer;
import com.hy.popeyegym.web.domain.user.User;
import com.hy.popeyegym.web.repository.pt.PtRepository;
import com.hy.popeyegym.web.repository.reservation.ReservationRepository;
import com.hy.popeyegym.web.repository.trainer.TrainerRepository;
import com.hy.popeyegym.web.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.hy.popeyegym.web.dto.request.ReservationRequestDto.*;
import static com.hy.popeyegym.web.dto.response.ReservationResponseDto.*;

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
        List<Reservation> schedules = reservationRepository.getSchedulesOfDate(trainerId, dateTime);
        if (!schedules.isEmpty()) throw new CustomException(ReservationType.ALREADY_RESERVED);

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
    public void cancel(Long userId, Long reservationId) {
        // 예약이 있는지 확인
        // 일단 단건 취소만 가능
//        List<Reservation> reservations = new ArrayList<>();
//        for (Long reservationId : reservationIds) {
//            Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, userId)
//                    .orElseThrow(() -> new CustomException(ReservationErrorType.NOT_CANCEL));
//            reservations.add(reservation);
//        }
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new CustomException(ReservationType.NOT_FOUND));

        boolean isExpired = reservation.getDateTime().isBefore(LocalDateTime.now());
        if (isExpired) throw new CustomException(ReservationType.EXPIRED);

        Pt pt = ptRepository.findByUserIdAndTrainerId(userId, reservation.getTrainer().getId())
                .orElseThrow(() -> new CustomException(ReservationType.NOT_ENROLL_PT));

        // 예약상태 reservation -> cancel
        reservation.cancel();

        // pt권 quantity +1
        pt.cancel();
    }

    /**
     * 트레이너의 예약 스케줄을 가져온다
     */
    public GetScheduleOfTrainerRes getScheduleOfTrainer(Long userId, Long trainerId, getScheduleOfTrainerReq req) {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startDt = req.getStartDt();
        if (today.isAfter(startDt)) startDt = today;

        List<Reservation> scheduleOfTrainer =
                reservationRepository.findScheduleOfTrainer(trainerId, startDt, req.getEndDt().with(LocalTime.MAX));

        List<ScheduleDetails> scheduleDetails =
                scheduleOfTrainer.stream()
                .map(o -> {
                    return ScheduleDetails.builder()
                            .reservationId(o.getId())
                            .dateTime(o.getDateTime())
                            .isMine(o.getUser().getId() == userId ? true: false)
                            .build();
                })
                .collect(Collectors.toList());

        return GetScheduleOfTrainerRes.builder()
                .trainerId(trainerId)
                .scheduleDetails(scheduleDetails)
                .build();
    }


    public Page<ReservationsRes> reservations(Long id, ReservationsReq req, Pageable pageable) {
        return reservationRepository.getReservations(id, req.getStatus(), req.getTrainerId(), pageable);
    }
}
