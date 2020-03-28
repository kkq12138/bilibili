package com.bilibili.common.config;


import com.bilibili.common.error.GlobalExceptionTranslator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {GlobalExceptionTranslator.class})
public class ExceptionConfig {
}
