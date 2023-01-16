package com.zhuang.openapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "my.openapi")
public class OpenApiProperties {

    // baseUrl
    private String baseUrl;
    // 用户名
    private String username;
    // 密码
    private String password;

}
