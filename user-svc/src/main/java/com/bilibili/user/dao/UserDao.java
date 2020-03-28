package com.bilibili.user.dao;

import com.bilibili.common.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    //手机号码或昵称的查询
    int soleValid(@Param("phone") String phone, @Param("userName") String name);

    //注册
    int signup(UserDto userDto);

    //密码登录
    int passwordLogin(UserDto userDto);

    //忘记密码
    int forgetPassword(UserDto userDto);
}
