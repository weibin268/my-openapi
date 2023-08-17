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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @OpenApi
    @ApiOperation("uploadFile")
    @PostMapping("uploadFile")
    public ApiResult<String> uploadFile(HttpServletRequest request) {
        List<String> fileNameList = new ArrayList<>();
        StandardMultipartHttpServletRequest multipartRequest = (StandardMultipartHttpServletRequest) request;
        for (Map.Entry<String, List<MultipartFile>> entry : multipartRequest.getMultiFileMap().entrySet()) {
            for (MultipartFile file : entry.getValue()) {
                fileNameList.add(file.getOriginalFilename());
            }
        }
        return ApiResult.success(fileNameList.stream().collect(Collectors.joining(",")));
    }

}
