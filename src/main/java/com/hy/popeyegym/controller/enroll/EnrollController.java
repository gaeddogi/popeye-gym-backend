package com.hy.popeyegym.controller.enroll;

import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.service.enroll.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hy.popeyegym.dto.response.EnrollResponseDto.GetTrainersRes;

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
