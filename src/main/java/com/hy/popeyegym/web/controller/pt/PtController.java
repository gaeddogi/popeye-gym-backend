package com.hy.popeyegym.web.controller.pt;

import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.web.service.pt.PtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hy.popeyegym.web.dto.response.PtResponseDto.GetUserPtInfoRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class PtController {

    private final PtService ptService;

    @GetMapping("pts")
    public List<GetUserPtInfoRes> getUserPtInfo(
            @AuthenticationPrincipal PrincipalDetails user
    ) {
        return ptService.getUserPtInfo(user.getUser().getId());
    }
}
