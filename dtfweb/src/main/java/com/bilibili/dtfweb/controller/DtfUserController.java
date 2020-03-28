package com.bilibili.dtfweb.controller;

import com.bilibili.common.dto.ResponseDto;
import com.bilibili.common.dto.UserDto;
import com.bilibili.dtfweb.utils.PublicUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Controller
@ResponseBody
@Validated
public class DtfUserController {

    //自动注入RestTemplateBuilder对象
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    //注册
    @PostMapping("/signup")

    @ApiOperation("注册，需要传入phone、userName、captcha、bizId、password")
    public ResponseDto signup(@Validated({UserDto.Signup.class, Default.class})
                                          UserDto userDto){
        String url="http://localhost:8085/user_svc/user/signup";
        //调用公有类的publicUtil方法发送请求
        return PublicUtil.publicUtil(restTemplateBuilder,url,userDto);
    }

    //发送短信
    @PostMapping("/send_sms")
    @ApiOperation("发送信息,state为0时代表发送注册验证码，state为1时代表验证码登录，state为2时代表发送重置密码验证码")
    public ResponseDto sendSes(@RequestParam @Pattern(regexp = "-?[0-2]\\d*",message = "状态码错误") String state,
                               @RequestParam @Pattern(regexp ="^\\d{11}$",message = "手机号码填写错误") String phone){
        RestTemplate restTemplate=restTemplateBuilder.build();
        String url="http://localhost:8085/user_svc/user/send_sms";
        Map<String,String> map=new HashMap<>();
        map.put("state",state);
        map.put("phone",phone);
        return restTemplate.postForObject(url,map,ResponseDto.class);
    }

    //密码登录
    @PostMapping("/password_login")
    @ApiOperation("密码登录，需要传入phone、password")
    public ResponseDto passwordLogin(@Validated({UserDto.PasswordLogin.class,Default.class})
                                     UserDto userDto){
        String url="http://localhost:8085/user_svc/user/password_login";
        //调用公有类的publicUtil方法发送请求
        return PublicUtil.publicUtil(restTemplateBuilder,url,userDto);
    }

    //验证码登录
    @PostMapping("/captcha_login")
    @ApiOperation("验证码登录，需要传入phone、captcha、bizId")
    public ResponseDto captchaLogin(@Validated({UserDto.CaptchaLogin.class,Default.class})
                                     UserDto userDto){
        String url="http://localhost:8085/user_svc/user/captcha_login";
        //调用公有类的publicUtil方法发送请求
        return PublicUtil.publicUtil(restTemplateBuilder,url,userDto);
    }

    //忘记密码
    @PostMapping("/forget_password")
    @ApiOperation("忘记密码，需要传入phone、password")
    public ResponseDto forgetPassword(@Validated({UserDto.ForgetPassword.class,Default.class}) UserDto userDto){
        String url="http://localhost:8085/user_svc/user/forget_password";
        //调用公有类的publicUtil方法发送请求
        return PublicUtil.publicUtil(restTemplateBuilder,url,userDto);
    }
}
