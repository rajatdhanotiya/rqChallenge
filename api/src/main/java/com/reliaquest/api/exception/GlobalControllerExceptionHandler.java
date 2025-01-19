package com.reliaquest.api.exception;

import com.reliaquest.api.dto.ErrorResponse;
import com.reliaquest.api.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> handleAPIException(APIException exception){
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.getErrorCode().getCode()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
