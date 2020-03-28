package com.bilibili.account.client;

import com.bilibili.common.dto.ResponseDto;
import com.bilibili.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "user-service", path = "/user", url = "http://localhost:8085/user_svc")
public interface AccountClient {

    //发送短信
    @PostMapping(path = "/send_sms")
    ResponseDto sendSms(@RequestBody Map<String, String> map);

    //密码登录
    @PostMapping(path="/password_login")
    ResponseDto login(@RequestBody UserDto usersDto);

    //注册
    @PostMapping(path="/signup")
    ResponseDto signup(@RequestBody UserDto usersDto);

    //忘记密码
    @PostMapping(path = "/forget_password")
    ResponseDto forgetPassword(@RequestBody UserDto usersDto);

    //验证码登录
    @PostMapping(path ="/captcha_login")
    ResponseDto captchaLogin(@RequestBody UserDto usersDto);
}