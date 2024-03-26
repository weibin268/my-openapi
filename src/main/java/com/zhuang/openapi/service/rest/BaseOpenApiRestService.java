package com.zhuang.openapi.service.rest;

import cn.hutool.core.util.StrUtil;
import com.zhuang.openapi.cache.CacheUtils;
import com.zhuang.openapi.config.OpenApiProperties;
import com.zhuang.openapi.model.ApiResult;
import com.zhuang.openapi.model.GetTokenArgs;
import com.zhuang.openapi.model.GetTokenResult;
import com.zhuang.openapi.util.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BaseOpenApiRestService {

    @Autowired
    private OpenApiProperties openApiProperties;

    protected RestTemplate restTemplate = RestTemplateUtils.getRestTemplate();

    public String getToken() {
        String token;
        String url = getFullUrl("/openapi/getToken");
        GetTokenArgs getTokenArgs = new GetTokenArgs();
        getTokenArgs.setUsername(openApiProperties.getUsername());
        getTokenArgs.setPassword(openApiProperties.getPassword());
        String cacheKey = getTokenCacheKey();
        token = CacheUtils.get(cacheKey);
        if (StrUtil.isNotEmpty(token)) {
            log.info("BaseOpenApiRestService.getToken -> from cache: {}", token);
            return token;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(getTokenArgs, headers);
        ResponseEntity<GetTokenResult> responseEntity = restTemplate.postForEntity(url, httpEntity, GetTokenResult.class);
        GetTokenResult restResult = getRestResult(responseEntity);
        token = restResult.getToken();
        Integer timeoutMinutes = restResult.getTimeoutMinutes();
        // 设置token缓存时间
        CacheUtils.set(cacheKey, token, (timeoutMinutes - 1) * 60);
        log.info("BaseOpenApiRestService.getToken -> from rest: {}", token);
        return token;
    }

    public void clearTokenCache() {
        CacheUtils.set(getTokenCacheKey(), null, 1);
    }

    protected String getTokenCacheKey() {
        return "restOpenApi:token:" + openApiProperties.getUsername();
    }

    protected String getFullUrl(String method) {
        String result = openApiProperties.getBaseUrl() + method;
        log.info("BaseOpenApiRestService.getFullUrl -> {}", result);
        return result;
    }

    protected HttpEntity<Object> getHttpEntity(Object args) {
        HttpHeaders headers = new HttpHeaders();
        String token = getToken();
        headers.set("token", StrUtil.format("{}", token));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(args, headers);
        return httpEntity;
    }

    protected <T extends ApiResult> T getRestResult(ResponseEntity<T> responseEntity) {
        return getRestResult(responseEntity, true);
    }

    protected <T extends ApiResult> T getRestResult(ResponseEntity<T> responseEntity, boolean handleResult) {
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(StrUtil.format("OpenApi接口请求失败！（状态码：{}）", responseEntity.getStatusCode().toString()));
        }
        T result = responseEntity.getBody();
        if (handleResult && result.getCode() != 0) {
            throw new RuntimeException(StrUtil.format("OpenApi接口请求失败！（错误代码：{}；错误信息：{}）", result.getCode(), result.getMessage()));
        }
        return result;
    }

}
