package com.hy.popeyegym.controller.enroll;

import com.hy.popeyegym.dto.request.EnrollRequestDto;
import com.hy.popeyegym.dto.response.EnrollResponseDto;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.service.enroll.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.hy.popeyegym.dto.request.EnrollRequestDto.*;
import static com.hy.popeyegym.dto.response.EnrollResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class EnrollController {

    private final EnrollService enrollService;

    /**
     * 등록된 트레이너 정보 가져오기
     */
    @GetMapping("enrolls")
    public List<GetTrainersRes> getTrainers(
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        return enrollService.getTrainers(user.getUser().getId());
    }
}
