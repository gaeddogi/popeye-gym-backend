package com.hy.popeyegym.web.admin.controller.trainer;

import com.hy.popeyegym.web.service.trainer.TrainerService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class TrainerControllerForAdmin {

    private final TrainerService trainerService;

    @PostMapping("/trainers")
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
    public Page<GetTrainerAllRes> trainerAll(
            @ModelAttribute GetTrainerAllReq req,
            @PageableDefault(size = 7) Pageable pageable
    ) {
        return trainerService.getTrainerAll(req, pageable);
    }

}
