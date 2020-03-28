package com.bilibili.common.error;


import com.bilibili.common.api.BaseResponse;
import com.bilibili.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionTranslator {

    @ExceptionHandler(ServiceException.class)
    public BaseResponse errorHandler(ServiceException e) {
        log.error("服务内部异常", e);
        return BaseResponse.builder().code(ResultCode.PARAM_BIND_ERROR)
                .message(e.getMessage()).build();
    }
}
