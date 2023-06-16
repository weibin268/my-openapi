package com.zhuang.openapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@MapperScan("com.zhuang.openapi.mapper")
public class YdOpenApiAutoConfig {

}
