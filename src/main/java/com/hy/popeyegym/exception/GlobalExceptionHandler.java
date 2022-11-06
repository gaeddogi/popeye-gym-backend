package com.hy.popeyegym.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getExceptionType()), e.getExceptionType().getHttpStatus());
    }

    //JsonParseException 클라이언트가 형식 잘못보내서 json파싱 불가능하면 이 에러남 이거 처리해야함.

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
//        log.warn("handleIllegalArgument", e);
//        ExceptionType errorCode = CommonErrorCode.INVALID_PARAMETER;
////        return handleExceptionInternal(errorCode, e.getMessage());
//        return createResponseEntity(errorCode);
//    }
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Object> handleAllException(Exception ex) {
//        log.warn("handleAllException", ex);
//        ExceptionType errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
//        return createResponseEntity(errorCode);
//    }
//
//
//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException e,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request
//    ) {
//        ExceptionType errorCode = CommonErrorCode.INVALID_PARAMETER;
//        return ResponseEntity
//                .status(errorCode.getHttpStatus())
//                .body(ErrorResponseDto.builder()
//                        .code(errorCode.name())
//                        .message(errorCode.getMessage())
//                        .validationErrors(
//                                e.getBindingResult().getFieldErrors().stream()
//                                        .map(fieldError -> ErrorResponseDto.ValidationError.of(fieldError))
//                                        .collect(Collectors.toList())
//                        )
//                        .build());
//    }
//
//
//
//    private ResponseEntity<Object> createResponseEntity (ExceptionType errorCode) {
//        return ResponseEntity
//                .status(errorCode.getHttpStatus())
//                .body(
//                        ErrorResponseDto.builder()
//                                .code(errorCode.name())
//                                .message(errorCode.getMessage())
//                                .build()
//                );
//    }

}
