package com.hy.popeyegym.web.controller.reservation;

import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.web.service.reservation.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import static com.hy.popeyegym.web.dto.request.ReservationRequestDto.*;
import static com.hy.popeyegym.web.dto.response.ReservationResponseDto.*;

@Api(tags = "예약")
@Tag(name = "예약",  description = "예약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * pt 예약
     */
    @PostMapping("reservations")
    @Operation(summary = "신규 예약", description = "트레이너 id와 날짜,시간 데이터를 받아 신규 예약을 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!", content = @Content(schema = @Schema(implementation = ReservationRes.class))),
    })
    public ResponseEntity reservation(
            @RequestBody ReservationReq req,
            @AuthenticationPrincipal PrincipalDetails user
            ) {

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
    @Operation(summary = "예약 취소", description = "파라미터로 넘어온 예약 id에 해당하는 예약건을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    public ResponseEntity cancel(
            @PathVariable(name = "id") Long reservationId,
            @AuthenticationPrincipal PrincipalDetails user
    ) {

        reservationService.cancel(user.getUser().getId(), reservationId);

        return ResponseEntity.ok().build();
    }

    /**
     * 트레이너 예약된 스케줄
     */
    @GetMapping("reservations/trainers/{trainerId}")
    @Operation(summary = "트레이너 예약 스케줄", description = "파라미터로 넘어온 기간동안의 트레이너 스케줄을 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
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
    @Operation(summary = "유저 예약 리스트", description = "트레이너별 유저 예약 리스트를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    public Page<ReservationsRes> reservations(
            @ModelAttribute ReservationsReq req,
            @AuthenticationPrincipal PrincipalDetails user,
            @PageableDefault(sort = {"dataTime"}, direction = Sort.Direction.DESC, size = 7) Pageable pageable
            ) {
        return reservationService.reservations(user.getUser().getId(), req, pageable);
    }
}
