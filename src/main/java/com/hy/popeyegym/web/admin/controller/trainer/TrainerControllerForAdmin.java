package com.hy.popeyegym.web.admin.controller.trainer;

import com.hy.popeyegym.web.dto.response.EnrollResponseDto;
import com.hy.popeyegym.web.service.trainer.TrainerService;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.hy.popeyegym.web.dto.request.TrainerRequestDto.EnrollReq;
import static com.hy.popeyegym.web.dto.request.TrainerRequestDto.GetTrainerAllReq;
import static com.hy.popeyegym.web.dto.response.TrainerResponseDto.EnrollRes;
import static com.hy.popeyegym.web.dto.response.TrainerResponseDto.GetTrainerAllRes;

@Api(tags = "admin")
@Tag(name = "admin",  description = "admin API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class TrainerControllerForAdmin {

    private final TrainerService trainerService;

    @PostMapping("/trainers")
    @Operation(summary = "트레이너 신규 등록", description = "트레이너를 신규 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!", content = @Content(schema = @Schema(implementation = EnrollResponseDto.EnrollSignUpResponseDto.class))),
    })
    public ResponseEntity<EnrollRes> enroll(
            @RequestBody EnrollReq req
    ) {

        Long trainerId = trainerService.enroll(req);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(trainerId)
                .toUri();

        return ResponseEntity.created(location).body(new EnrollRes(trainerId));
    }

    @GetMapping("/trainers")
    @Operation(summary = "모든 트레이너 목록", description = "검색 조건에 해당하는 모든 트레이너 목록을 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!", content = @Content(schema = @Schema(implementation = EnrollResponseDto.EnrollSignUpResponseDto.class))),
    })
    public Page<GetTrainerAllRes> trainerAll(
            @ModelAttribute GetTrainerAllReq req,
            @PageableDefault(size = 7) Pageable pageable
    ) {
        return trainerService.getTrainerAll(req, pageable);
    }

}
