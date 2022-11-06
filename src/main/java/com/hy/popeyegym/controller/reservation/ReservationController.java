package com.hy.popeyegym.controller.reservation;

import com.hy.popeyegym.dto.response.EnrollResponseDto;
import com.hy.popeyegym.dto.response.ReservationResponseDto;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

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
        reservationService.reservation(user.getUser().getId(), req);

//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(reservationId)
//                .toUri();
//
//        return ResponseEntity.created(location).body(new ReservationRes(reservationId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 예약 취소
     * @param id
     * @param reserCancelRequestDto
     * @return
     */
    @PostMapping("reservations/{id}")
    public ResponseEntity cancel(
            @PathVariable(name = "id") Long id,
            @RequestBody ReserCancelRequestDto reserCancelRequestDto
    ) {
        reserCancelRequestDto.setReservationId(id);
        reservationService.cancel(reserCancelRequestDto);

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

}
