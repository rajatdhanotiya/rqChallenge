package com.reliaquest.api.exception;

import com.reliaquest.api.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;
}
