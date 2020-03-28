package com.bilibili.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//统一响应对象
public class ResponseDto {
    //状态码
    private int code;

    //信息
    private String message;

    //具体内容
    private String data;

    //是否成功
    private Boolean succeed;

}
