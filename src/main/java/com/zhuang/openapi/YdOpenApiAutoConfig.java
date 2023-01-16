package com.zhuang.openapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@MapperScan("com.yd.openapi.mapper")
public class YdOpenApiAutoConfig {

}
