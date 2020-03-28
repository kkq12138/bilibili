package com.bilibili.user.validator;

import com.bilibili.common.exception.ParameterException;

import com.bilibili.common.utils.ResponseUtil;
import com.bilibili.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class SendSmsValidated implements ConstraintValidator<SendSmsValid, Map<String, String>> {

    @Autowired
    UserDao userDao;

    @Override
    public void initialize(SendSmsValid constraintAnnotation) {
    }

    //注解实现方法
    @Override
    public boolean isValid(Map<String, String> map, ConstraintValidatorContext constraintValidatorContext) {
        //调用dao层方法查询手机号码是否已存在
        int result = userDao.soleValid(map.get("phone"), null);
        //如果状态码为0且结果大于0，说明该手机号码已存在不能再发送注册验证码，并抛出自定义的参数异常
        if ("0".equals(map.get("state")) && result > 0) {
            throw new ParameterException("手机号码已存在");
        }
        //如果状态码为1或2且结果小于0，说明该手机号码不存在，不能发送忘记密码或登录验证码，并抛出自定义的参数异常
        if ("1".equals(map.get("state")) || "2".equals(map.get("state")) && result == 0) {
            throw new ParameterException("手机号码未存在");
        }
        return true;
    }
}
