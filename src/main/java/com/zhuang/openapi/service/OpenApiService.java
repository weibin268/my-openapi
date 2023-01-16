package com.zhuang.openapi.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhuang.openapi.model.GetTokenArgs;
import com.zhuang.openapi.model.GetTokenResult;
import com.zhuang.openapi.model.OpenApiUserInfo;
import com.zhuang.openapi.pojo.user.YdOpenapiUser;
import com.zhuang.openapi.pojo.user.YdOpenapiUserRef;
import com.zhuang.openapi.service.user.YdOpenapiUserRefService;
import com.zhuang.openapi.service.user.YdOpenapiUserService;
import com.zhuang.openapi.util.JwtUtils;
import com.zhuang.openapi.util.SpringWebUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OpenApiService {

    private static final String TOKEN_KEY = "token";

    @Autowired
    private YdOpenapiUserService ydOpenapiUserService;
    @Autowired
    private YdOpenapiUserRefService ydOpenapiUserRefService;

    /**
     * 获取token
     *
     * @param args
     * @return
     */
    public GetTokenResult getToken(GetTokenArgs args) {
        GetTokenResult result = new GetTokenResult();
        YdOpenapiUser ydOpenapiUser = ydOpenapiUserService.getByUsername(args.getUsername());
        if (ydOpenapiUser == null || !args.getPassword().equals(ydOpenapiUser.getPassword())) {
            throw new RuntimeException("invalid username  or password!");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", ydOpenapiUser.getId());
        claims.put("username", args.getUsername());
        claims.put("password", args.getPassword());
        List<YdOpenapiUserRef> ydOpenapiUserRefList = ydOpenapiUserRefService.getListByOpenapiUserId(ydOpenapiUser.getId());
        List<OpenApiUserInfo.RefInfo> refList = ydOpenapiUserRefList.stream().map(item -> {
            OpenApiUserInfo.RefInfo refInfo = new OpenApiUserInfo.RefInfo();
            refInfo.setRefTable(item.getRefTable());
            refInfo.setRefId(item.getRefId());
            return refInfo;
        }).collect(Collectors.toList());
        claims.put("refList", refList);
        Integer timeoutMinutes = 60;
        String token = JwtUtils.createToken(claims, DateUtil.offsetMinute(new Date(), timeoutMinutes));
        result.setToken(token);
        result.setTimeoutMinutes(timeoutMinutes);
        return result;
    }

    public OpenApiUserInfo checkToken() {
        String token = SpringWebUtils.getRequest().getHeader(TOKEN_KEY);
        if (StrUtil.isEmpty(token)) {
            token = SpringWebUtils.getRequest().getParameter(TOKEN_KEY);
        }
        if (StrUtil.isEmpty(token)) {
            throw new RuntimeException("no token provided!");
        }
        OpenApiUserInfo openApiUserInfo = parseToken(token);
        return openApiUserInfo;
    }

    public OpenApiUserInfo parseToken(String token) {
        OpenApiUserInfo openApiUserInfo = new OpenApiUserInfo();
        try {
            Claims claims = JwtUtils.parseToken(token);
            openApiUserInfo.setUserId(claims.get("userId").toString());
            openApiUserInfo.setUsername(claims.get("username").toString());
            openApiUserInfo.setPassword(claims.get("password").toString());
            List<Map> refMapList = (List<Map>) claims.get("refList");
            List<OpenApiUserInfo.RefInfo> refList = refMapList.stream().map(item -> {
                OpenApiUserInfo.RefInfo refInfo = new OpenApiUserInfo.RefInfo();
                refInfo.setRefTable(item.get("refTable").toString());
                refInfo.setRefId(item.get("refId").toString());
                return refInfo;
            }).collect(Collectors.toList());
            openApiUserInfo.setRefList(refList);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RuntimeException("token expired!");
        } catch (Exception e) {
            throw new RuntimeException("token parse error!");
        }
        return openApiUserInfo;
    }

}
