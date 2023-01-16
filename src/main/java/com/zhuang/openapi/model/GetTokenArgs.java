package com.zhuang.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel("获取openApiToken参数")
public class GetTokenArgs {
    @NotEmpty(message = "username can't be empty!")
    @ApiModelProperty("用户名")
    private String username;
    @NotEmpty(message = "password can't be empty!")
    @ApiModelProperty("密码")
    private String password;
}
