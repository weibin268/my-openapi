package com.zhuang.openapi.util;

import com.zhuang.openapi.enums.OpenApiUserRefType;
import com.zhuang.openapi.model.OpenApiUserInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OpenApiUtils {

    private static ThreadLocal<OpenApiUserInfo> openApiUserInfoThreadLocal = new ThreadLocal<>();

    public static void setOpenApiUser(OpenApiUserInfo openApiUserInfo) {
        openApiUserInfoThreadLocal.set(openApiUserInfo);
    }

    public static OpenApiUserInfo getOpenApiUser() {
        return openApiUserInfoThreadLocal.get();
    }

    public static List<String> getRefIdList(OpenApiUserRefType refType) {
        OpenApiUserInfo openApiUser = getOpenApiUser();
        if (openApiUser == null) return Collections.EMPTY_LIST;
        return openApiUser.getRefList().stream().filter(c -> c.getRefTable().equals(refType.getRefTable())).map(c -> c.getRefId()).collect(Collectors.toList());
    }

}
