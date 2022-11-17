package com.hy.popeyegym.admin.controller.trainer;

import com.hy.popeyegym.service.trainer.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.hy.popeyegym.dto.request.TrainerRequestDto.*;
import static com.hy.popeyegym.dto.response.TrainerResponseDto.*;

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
    public List<GetTrainerAllRes> trainerAll(
            @ModelAttribute GetTrainerAllReq req
    ) {
        return trainerService.getTrainerAll(req);
    }

}
