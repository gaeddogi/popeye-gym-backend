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

import static com.hy.popeyegym.dto.request.EnrollRequestDto.*;
import static com.hy.popeyegym.dto.response.EnrollResponseDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class EnrollController {

    private final EnrollService enrollService;

    @GetMapping("test")
    public void test(@AuthenticationPrincipal PrincipalDetails user) {
        System.out.println("=====user: " + user);
    }

    /**
     * pt 등록
     */
    @PostMapping("enrolls")
    public ResponseEntity<EnrollSignUpResponseDto> enroll(
            @RequestBody EnrollSignUpRequestDto enrollSignUpDto,
            @AuthenticationPrincipal PrincipalDetails user
            ) {

        Long enrollId = enrollService.enroll(enrollSignUpDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enrollId)
                .toUri();

        return ResponseEntity.created(location).body(new EnrollSignUpResponseDto(enrollId));
    }
}
