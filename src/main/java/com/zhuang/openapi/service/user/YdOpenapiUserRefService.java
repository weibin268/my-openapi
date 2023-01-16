package com.zhuang.openapi.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.openapi.pojo.user.YdOpenapiUserRef;
import com.zhuang.openapi.mapper.user.YdOpenapiUserRefMapper;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.List;

/**
 * <p>
 * OpenApi用户资源关联表 服务实现类
 * </p>
 *
 * @author admin，IP：10.147.20.136、172.19.64.1、172.21.64.1、172.24.128.1、172.25.64.1、172.28.64.1、172.29.32.1、172.30.208.1、192.168.1.4、192.168.6.1、192.168.73.1
 * @since 2023-01-10
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class YdOpenapiUserRefService extends ServiceImpl<YdOpenapiUserRefMapper, YdOpenapiUserRef> {

    public List<YdOpenapiUserRef> getListByOpenapiUserId(String openapiUserId) {
        return list(new LambdaQueryWrapper<YdOpenapiUserRef>()
                .eq(YdOpenapiUserRef::getOpenapiUserId, openapiUserId)
        );
    }

}
