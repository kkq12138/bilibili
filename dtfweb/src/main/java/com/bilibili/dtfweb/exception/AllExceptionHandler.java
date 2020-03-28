package com.bilibili.dtfweb.exception;

import com.bilibili.common.dto.ResponseDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AllExceptionHandler {

    //拦截所有参数验证失败的异常，并返回给前端
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseDto exceptionHandler(ConstraintViolationException e) {
        //截取需要的信息
        String message=e.getMessage();
        message=message.substring(message.lastIndexOf("[")+1,message.lastIndexOf("]"));
        return new ResponseDto(403,message,null,false);
    }

}
