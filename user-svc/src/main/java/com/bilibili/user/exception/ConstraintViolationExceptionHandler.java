package com.bilibili.user.exception;

import com.bilibili.common.dto.ResponseDto;
import com.bilibili.common.exception.ParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ConstraintViolationExceptionHandler {

    //拦截自定义的参数异常，返回响应对象
    @ExceptionHandler(ParameterException.class)
    @ResponseBody
    public ResponseDto exceptionHandler(ParameterException e) {
        return new ResponseDto(400, e.getMes(), null, false);
    }

    //拦截阿里云发送短信的空指针异常，返回响应对象
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseDto exceptionHandler(NullPointerException e) {
        return new ResponseDto(403, "操作过于频繁请稍后再试", null, false);
    }

}
