package com.bilibili.common.exception;

import lombok.Getter;

@Getter
//自定义的参数异常
public class ParameterException extends RuntimeException {

    private int code;

    private String mes;

    public ParameterException(String message){
        super(message);
        this.mes=message;
    }
}
