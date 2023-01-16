package com.zhuang.openapi.aop;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zhuang.openapi.annotation.OpenApi;
import com.zhuang.openapi.model.OpenApiUserInfo;
import com.zhuang.openapi.service.OpenApiService;
import com.zhuang.openapi.service.log.YdOpenapiLogService;
import com.zhuang.openapi.util.OpenApiUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class OpenApiAspect {

    @Autowired
    private OpenApiService openApiService;
    @Autowired
    private YdOpenapiLogService ydOpenapiLogService;

    @Pointcut("@annotation(com.zhuang.openapi.annotation.OpenApi)")
    public void openapi() {
    }

    @Before("openapi()")
    public void doBefore(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        //System.out.println("AOP拦截:" + signature.getDeclaringTypeName() + "." + signature.getName());
    }

    @After("openapi()")
    public void doAfter(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        //System.out.println("AOP拦截:" + signature.getDeclaringTypeName() + "." + signature.getName());
    }

    @Around("openapi()")
    public Object doOperationLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        long startTimeMillis = System.currentTimeMillis();
        String argsJson = null;
        if (proceedingJoinPoint.getArgs() != null) {
            argsJson = JSON.toJSONString(proceedingJoinPoint.getArgs());
        }
        String apiClass = signature.getDeclaringTypeName();
        String apiMethod = signature.getName();
        String apiName = apiClass + "." + apiMethod;
        Method method = ((MethodSignature) signature).getMethod();
        OpenApi openApiAnnotation = method.getAnnotation(OpenApi.class);
        if (StrUtil.isNotEmpty(openApiAnnotation.name())) {
            apiName = openApiAnnotation.name();
        }
        OpenApiUserInfo openApiUserInfo = null;
        if (openApiAnnotation.checkToken()) {
            openApiUserInfo = openApiService.checkToken();
            OpenApiUtils.setOpenApiUser(openApiUserInfo);
        }
        Object result = proceedingJoinPoint.proceed();
        long endTimeMillis = System.currentTimeMillis();
        ydOpenapiLogService.add(openApiUserInfo == null ? null : openApiUserInfo.getUserId(), apiName, argsJson, new Long(endTimeMillis - startTimeMillis).intValue());
        return result;
    }


}
