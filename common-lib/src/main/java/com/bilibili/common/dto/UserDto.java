package com.bilibili.common.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Getter
@Setter
public class UserDto {

  //注册效验组
  public interface Signup{};

  //密码登录效验组
  public interface PasswordLogin{};

  //验证码登录效验组
  public interface CaptchaLogin{};

  //忘记密码效验组
  public interface ForgetPassword{};

  @NotEmpty(message = "手机号码不能为空")
  @Pattern(regexp = "^\\d{11}$",message = "手机号码格式不正确")
  private String phone;

  @NotEmpty(groups = Signup.class,message = "昵称不能为空")
  @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$",message = "昵称填写错误")
  @Size(min = 3,max = 10,message = "昵称填写错误")
  private String userName;

  @NotEmpty(groups = {Signup.class,ForgetPassword.class,CaptchaLogin.class},message = "验证码不能为空")
  @Pattern(regexp = "^\\d{6}$",message = "验证码填写错误")
  private String captcha;

  @NotEmpty(groups = {Signup.class,CaptchaLogin.class,ForgetPassword.class},message = "短信流水号不能为空")
  private String bizId;

  @NotEmpty(groups = {Signup.class,PasswordLogin.class,ForgetPassword.class},message = "密码不能为空")
  @Pattern(regexp ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,15}$",message = "密码填写错误")
  private String password;
  private String intro;
  private String sex;
  private Date birthday;
  private String icon;
  private String state;
  private String power;

  public void encryptionPassword() throws NoSuchAlgorithmException {
    MessageDigest messageDigest=MessageDigest.getInstance("MD5");
    messageDigest.update(this.password.getBytes());
    this.password=new BigInteger(1,messageDigest.digest()).toString(16);
  }
}
