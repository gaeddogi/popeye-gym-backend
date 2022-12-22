package com.hy.popeyegym.web.admin.controller.enroll;

import com.hy.popeyegym.web.service.enroll.EnrollService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Api(tags = "admin")
@Tag(name = "admin",  description = "admin API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/")
public class EnrollControllerForAdmin {

    private final EnrollService enrollService;

    /**
     * pt 등록
     */
    @PostMapping("enrolls")
    @Operation(summary = "수강권 신규 등록", description = "유저의 수강권을 신규 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!", content = @Content(schema = @Schema(implementation = EnrollSignUpResponseDto.class))),
    })
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
