package com.hy.popeyegym.admin.controller.enroll;

import com.hy.popeyegym.dto.request.EnrollRequestDto;
import com.hy.popeyegym.dto.response.EnrollResponseDto;
import com.hy.popeyegym.security.PrincipalDetails;
import com.hy.popeyegym.service.enroll.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.hy.popeyegym.dto.request.EnrollRequestDto.*;
import static com.hy.popeyegym.dto.response.EnrollResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class EnrollControllerForAdmin {

    private final EnrollService enrollService;

    /**
     * pt 등록
     */
    @PostMapping("enrolls")
    public ResponseEntity<EnrollSignUpResponseDto> enroll(
            @RequestBody EnrollSignUpRequestDto enrollSignUpDto
    ) {

        Long enrollId = enrollService.enroll(enrollSignUpDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enrollId)
                .toUri();

        return ResponseEntity.created(location).body(new EnrollSignUpResponseDto(enrollId));
    }
}
