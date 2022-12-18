package com.hy.popeyegym.web.admin.controller.enroll;

import com.hy.popeyegym.web.service.enroll.EnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.hy.popeyegym.web.dto.request.EnrollRequestDto.EnrollSignUpRequestDto;
import static com.hy.popeyegym.web.dto.response.EnrollResponseDto.EnrollSignUpResponseDto;

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
