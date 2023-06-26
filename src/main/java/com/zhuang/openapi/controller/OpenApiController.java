package com.zhuang.openapi.controller;

import com.zhuang.openapi.annotation.OpenApi;
import com.zhuang.openapi.model.ApiResult;
import com.zhuang.openapi.model.GetTokenArgs;
import com.zhuang.openapi.model.GetTokenResult;
import com.zhuang.openapi.model.OpenApiUserInfo;
import com.zhuang.openapi.service.OpenApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "OpenApi接口")
@RequestMapping("/openapi")
@RestController
public class OpenApiController {

    @Autowired
    private OpenApiService openApiService;

    @OpenApi(checkToken = false)
    @ApiOperation("获取Token")
    @PostMapping("getToken")
    public GetTokenResult getToken(@RequestBody GetTokenArgs getTokenArgs) {
        GetTokenResult token = openApiService.getToken(getTokenArgs);
        return token;
    }

    @OpenApi(checkToken = false)
    @ApiOperation("解析Token")
    @GetMapping("parseToken")
    public ApiResult<OpenApiUserInfo> getToken(@RequestParam("token") String token) {
        ApiResult<OpenApiUserInfo> resultVo = new ApiResult<>();
        OpenApiUserInfo openApiUserInfo = openApiService.parseToken(token);
        resultVo.setData(openApiUserInfo);
        return resultVo;
    }

    @OpenApi
    @ApiOperation("sayHello")
    @GetMapping("sayHello")
    public ApiResult<String> sayHello(@RequestParam("name") String name) {
        return ApiResult.success(name);
    }
}
