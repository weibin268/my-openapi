package com.zhuang.openapi.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.zhuang.openapi.model.GetTokenArgs;
import com.zhuang.openapi.model.GetTokenResult;
import com.zhuang.openapi.model.OpenApiUserInfo;
import com.zhuang.openapi.pojo.user.SysOpenapiUser;
import com.zhuang.openapi.pojo.user.SysOpenapiUserRef;
import com.zhuang.openapi.service.user.SysOpenapiUserRefService;
import com.zhuang.openapi.service.user.SysOpenapiUserService;
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
    private SysOpenapiUserService sysOpenapiUserService;
    @Autowired
    private SysOpenapiUserRefService sysOpenapiUserRefService;

    /**
     * 获取token
     *
     * @param args
     * @return
     */
    public GetTokenResult getToken(GetTokenArgs args) {
        GetTokenResult result = new GetTokenResult();
        SysOpenapiUser sysOpenapiUser = sysOpenapiUserService.getByUsername(args.getUsername());
        if (sysOpenapiUser == null || !args.getPassword().equals(sysOpenapiUser.getPassword())) {
            throw new RuntimeException("invalid username  or password!");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", sysOpenapiUser.getId());
        claims.put("username", args.getUsername());
        claims.put("password", args.getPassword());
        List<SysOpenapiUserRef> sysOpenapiUserRefList = sysOpenapiUserRefService.getListByOpenapiUserId(sysOpenapiUser.getId());
        List<OpenApiUserInfo.RefInfo> refList = sysOpenapiUserRefList.stream().map(item -> {
            OpenApiUserInfo.RefInfo refInfo = new OpenApiUserInfo.RefInfo();
            refInfo.setRefTable(item.getRefTable());
            refInfo.setRefId(item.getRefId());
            return refInfo;
        }).collect(Collectors.toList());
        claims.put("refList", refList);
        int timeoutMinutes = 60;
        int actualTimeoutMinutes = timeoutMinutes + 10;
        String token = JwtUtils.createToken(claims, DateUtil.offsetMinute(new Date(), actualTimeoutMinutes));
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
        return parseToken(token);
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
