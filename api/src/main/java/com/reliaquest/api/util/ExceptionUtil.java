package com.reliaquest.api.util;

import com.reliaquest.api.enums.ErrorCode;
import com.reliaquest.api.exception.APIException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtil {

    private MessageSource messageSource;

    public ExceptionUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public APIException getAPIException(ErrorCode errorCode, Object[] customArgs){
        String eCode = errorCode.getCode();
        String errMsg = messageSource.getMessage(eCode, customArgs, null);
        return new APIException(errorCode, errMsg);
    }


}
