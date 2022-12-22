package com.hy.popeyegym.web.controller.enroll;

import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.web.service.enroll.EnrollService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hy.popeyegym.web.dto.response.EnrollResponseDto.GetTrainersRes;

@Api(tags = "등록",  value = "등록 API")
@Tag(name = "등록", description = "등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class EnrollController {

    private final EnrollService enrollService;

    /**
     * 등록된 트레이너 정보 가져오기
     */
    @Operation(summary = "등록한 트레이너", description = "회원이 등록한 트레이너의 리스트를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!", content = @Content(schema = @Schema(implementation = GetTrainersRes.class))),
    })
    @GetMapping("enrolls")
    public List<GetTrainersRes> getTrainers(
            @AuthenticationPrincipal @Parameter() PrincipalDetails user
    ) {
        return enrollService.getTrainers(user.getUser().getId());
    }
}
