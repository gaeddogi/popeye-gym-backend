package com.hy.popeyegym.controller.pt;

import com.hy.popeyegym.dto.response.PtResponseDto;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.service.pt.PtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hy.popeyegym.dto.response.PtResponseDto.*;

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
