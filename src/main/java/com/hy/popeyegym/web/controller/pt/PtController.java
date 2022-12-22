package com.hy.popeyegym.web.controller.pt;

import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.web.service.pt.PtService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
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

import static com.hy.popeyegym.web.dto.response.PtResponseDto.GetUserPtInfoRes;

@Api(tags = "수강권")
@Tag(name = "수강권",  description = "수강권 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class PtController {

    private final PtService ptService;

    @GetMapping("pts")
    @Operation(summary = "유저 수강권 정보", description = "유저가 등록한 트레이너 목록과 남은 수량을 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!", content = @Content(schema = @Schema(implementation = GetUserPtInfoRes.class))),
    })
    public List<GetUserPtInfoRes> getUserPtInfo(
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        return ptService.getUserPtInfo(user.getUser().getId());
    }
}
