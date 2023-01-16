package com.zhuang.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取openApiToken结果")
public class GetTokenResult extends ApiResult {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("有效时间（单位：分钟）")
    private Integer timeoutMinutes;
}
