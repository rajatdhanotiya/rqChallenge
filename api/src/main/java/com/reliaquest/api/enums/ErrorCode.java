package com.reliaquest.api.enums;

public enum ErrorCode {
    INTERNAL_SERVER_ERROR("EMP_01"),
    NO_RECORDS_FOUND("EMP_02"),
    INVALID_INPUT("EMP_03"),
    EXCEPTION_WHILE_CALLING_EXTERNAL_API("EMP_04");

    private final String code;

    ErrorCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
