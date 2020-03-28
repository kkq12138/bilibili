package com.bilibili.user.validator;


import com.bilibili.common.dto.UserDto;
import com.bilibili.common.exception.ParameterException;
import com.bilibili.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidated implements ConstraintValidator<NameValid, UserDto> {

    //自动注入UserDao层方法
    @Autowired
    UserDao userDao;

    @Override
    public void initialize(NameValid constraintAnnotation) {
    }

    //注解实现方法
    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        //调用dao层方法，返回查询的结果，如果结果大于1，则抛出自定义的参数异常，否则返回true
        if (userDao.soleValid(null, userDto.getUserName()) > 0) {
            throw new ParameterException("昵称已存在");
        }
        return true;
    }

}
