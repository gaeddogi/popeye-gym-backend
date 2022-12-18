package com.hy.popeyegym.controller.reservation;

import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.hy.popeyegym.dto.request.ReservationRequestDto.*;
import static com.hy.popeyegym.dto.response.ReservationResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * pt 예약
     */
    @PostMapping("reservations")
    public ResponseEntity reservation(
            @RequestBody ReservationReq req,
            @AuthenticationPrincipal PrincipalDetails user
            ) {

        System.out.println(req);
        Long reservationId = reservationService.reservation(user.getUser().getId(), req);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationId)
                .toUri();

        return ResponseEntity.created(location).body(new ReservationRes(reservationId));
    }

    /**
     * 예약 취소
     */
    @PostMapping("reservations/{id}")
    public ResponseEntity cancel(
            @PathVariable(name = "id") Long reservationId,
            @AuthenticationPrincipal PrincipalDetails user
    ) {
//        reserCancelRequestDto.setReservationId(id);

        System.out.println("예약취소: " + reservationId);
        reservationService.cancel(user.getUser().getId(), reservationId);

        return ResponseEntity.ok().build();
    }

    /**
     * 트레이너 예약된 스케줄
     */
    @GetMapping("reservations/trainers/{trainerId}")
    public GetScheduleOfTrainerRes getScheduleOfTrainer(
            @PathVariable(name = "trainerId") Long trainerId,
            @ModelAttribute getScheduleOfTrainerReq req,
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        return reservationService.getScheduleOfTrainer(user.getUser().getId(), trainerId, req);
    }

    /**
     * 유저 예약 리스트
     */
    @GetMapping("reservations")
    public Page<ReservationsRes> reservations(
            @ModelAttribute ReservationsReq req,
            @AuthenticationPrincipal PrincipalDetails user,
            @PageableDefault(sort = {"dataTime"}, direction = Sort.Direction.DESC, size = 7) Pageable pageable
            ) {
        return reservationService.reservations(user.getUser().getId(), req, pageable);
    }
}
