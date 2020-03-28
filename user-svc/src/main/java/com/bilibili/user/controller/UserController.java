package com.bilibili.user.controller;


import com.bilibili.common.dto.ResponseDto;
import com.bilibili.common.dto.UserDto;
import com.bilibili.user.service.UserService;
import com.bilibili.user.validator.SendSmsValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    Environment env;

    @Autowired
    UserService userService;

    //注册
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody @Valid UserDto userDto) throws NoSuchAlgorithmException {
        return userService.signup(userDto);
    }

    //密码登录
    @PostMapping("/password_login")
    public ResponseDto passwordLogin(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        return userService.passwordLogin(userDto);
    }

    //验证码登录
    @PostMapping("/captcha_login")
    public ResponseDto captchaLogin(@RequestBody UserDto userDto) {
        return userService.captchaLogin(userDto);
    }

    //忘记密码
    @PostMapping("/forget_password")
    public ResponseDto forgetPassword(@RequestBody UserDto userDto) throws NoSuchAlgorithmException {
        return userService.forgetPassword(userDto);
    }

    //发送短信
    @PostMapping("/send_sms")
    public ResponseDto sendSms(@RequestBody @SendSmsValid Map<String, String> map) {
        return userService.sendSms(map);
    }
}

