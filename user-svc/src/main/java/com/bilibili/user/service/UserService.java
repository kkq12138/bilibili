package com.bilibili.user.service;

import com.bilibili.common.dto.ResponseDto;
import com.bilibili.common.dto.UserDto;
import com.bilibili.common.utils.ResponseUtil;
import com.bilibili.user.utils.SmsUtil;
import com.bilibili.user.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class UserService {

    //创建日志对象
    Logger logger=Logger.getLogger(UserService.class);

    //自动注入userDao对象
    @Autowired
    UserDao userDao;

    //自动注入短信工具类对象
    @Autowired
    SmsUtil smsUtil;

    //注册
    public ResponseDto signup(UserDto userDto) throws NoSuchAlgorithmException {
        //判断验证码是否正确，如果不正确记录日志后直接返回
        if (!userDto.getCaptcha().equals(smsUtil.querySendDetails(userDto.getPhone(), userDto.getBizId()))) {
            logger.info("手机号码为:"+userDto.getPhone()+"的用户验证码输入错误");
            return new ResponseDto(400, "验证码错误", null, false);
        }
        //将密码进行加密
        userDto.encryptionPassword();
        //调用dao层的方法进行添加操作，返回受影响的行数,
        // 并判断受影响的行数是否大于0，如果大于0记录日志后返回正确响应对象，否则记录日志后返回失败响应对象
        if (userDao.signup(userDto) > 0) {
            logger.info("手机号码为:"+userDto.getPhone()+"的用户注册成功");
            return new ResponseDto(200, "注册成功", null, true);
        }
        logger.error("手机号码为:"+userDto.getPhone()+"的用户由于未知原因注册失败");
        return ResponseUtil.createErrorResponseDto();
    }

    //验证码登录
    public ResponseDto captchaLogin(UserDto userDto) {
        //判断验证码是否正确，如果正确记录日志后返回正确响应对象，否则记录日志后返回失败响应对象
        if (userDto.getCaptcha().
                equals(smsUtil.querySendDetails(userDto.getPhone(), userDto.getBizId()))) {
            logger.info("手机号码为:"+userDto.getPhone()+"的用户使用验证码登录成功");
            return new ResponseDto(200, "验证码正确", null, true);
        }
        logger.info("手机号码为:"+userDto.getPhone()+"的用户由于验证码错误而登录失败");
        return new ResponseDto(400, "验证码错误", null, false);
    }


    //登录
    public ResponseDto passwordLogin(UserDto userDto) throws NoSuchAlgorithmException {
        //将密码进行加密
        userDto.encryptionPassword();
        //调用dao层的方法，查询该账号，返回查询结果，
        // 并判断结果是否大于0,如果大于0记录日志后返回成功响应对象，否则记录日志后返回失败响应对象
        if (userDao.passwordLogin(userDto) > 0) {
            logger.info("手机号码为:"+userDto.getPhone()+"的用户使用密码登录成功");
            return new ResponseDto(200, "登录成功", null, true);
        }
        logger.info("手机号码为:"+userDto.getPhone()+"的用户因为用户名或密码错误登录失败");
        return new ResponseDto(400, "用户名或密码错误", null, false);
    }

    //发送短信
    public ResponseDto sendSms(Map<String, String> map) {
        //调用短信工具类的发送信息方法，并记录日志后返回短信流水号
        String result = smsUtil.sendSms(map.get("state"), map.get("phone"));
        if ("0".equals(map.get("state"))){
            logger.info("向手机号码为:"+map.get("phone")+"的用户送了一条注册短信");
        }else if ("1".equals(map.get("state"))){
            logger.info("向手机号码为:"+map.get("phone")+"的用户送了一条验证码登录短信");
        }else{
            logger.info("向手机号码为:"+map.get("phone")+"的用户送了一条修改密短信");
        }
        return new ResponseDto(200, "发送成功", result, true);
    }

    //忘记密码
    public ResponseDto forgetPassword(UserDto userDto) throws NoSuchAlgorithmException {
        //将密码加密
        userDto.encryptionPassword();
        //调用dao层方法，修改账号信息，返回受影响的行数
        //并判断结果是否大于0，如果大于0记录日志返回后成功响应对象，否则记录日志后返回失败响应对象
        if (userDao.forgetPassword(userDto) > 0) {
            logger.info("手机号码为:"+userDto.getPhone()+"的用户修改了密码");
            return new ResponseDto(200, "修改密码成功", null, true);
        }
        logger.error("手机号码为:"+userDto.getPhone()+"的用户由于未知原因修改密码失败");
        return new ResponseDto(400, "系统繁忙，请稍后再试", null, false);
    }

}
