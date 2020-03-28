package com.bilibili.dtfweb.utils;

import com.bilibili.common.dto.ResponseDto;
import com.bilibili.common.dto.UserDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class PublicUtil {

    //controller层的重复代码，代替controller层发送请求
    public static ResponseDto publicUtil(RestTemplateBuilder restTemplateBuilder, String url, UserDto userDto){
        RestTemplate restTemplate=restTemplateBuilder.build();
        HttpEntity<UserDto> httpEntity=new HttpEntity<>(userDto);
        return restTemplate.postForObject(url,httpEntity, ResponseDto.class);
    }
}
