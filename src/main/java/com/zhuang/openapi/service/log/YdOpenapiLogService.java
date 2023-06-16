package com.zhuang.openapi.service.log;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.openapi.pojo.log.YdOpenapiLog;
import com.zhuang.openapi.mapper.log.YdOpenapiLogMapper;
import com.zhuang.openapi.util.RequestUtils;
import com.zhuang.openapi.util.SpringWebUtils;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Date;

/**
 * <p>
 * OpenApi调用日志 服务实现类
 * </p>
 *
 * @author admin，IP：10.147.20.136、172.19.64.1、172.21.64.1、172.24.128.1、172.25.64.1、172.28.64.1、172.29.32.1、172.30.208.1、192.168.1.4、192.168.6.1、192.168.73.1
 * @since 2023-01-09
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class YdOpenapiLogService extends ServiceImpl<YdOpenapiLogMapper, YdOpenapiLog> {

    public void add(String openapiUserId, String apiName, String apiParams, Integer apiExecuteTimes) {
        YdOpenapiLog ydOpenapiLog = new YdOpenapiLog();
        ydOpenapiLog.setOpenapiUserId(openapiUserId);
        ydOpenapiLog.setApiName(apiName);
        ydOpenapiLog.setApiParams(apiParams);
        ydOpenapiLog.setApiExecuteTimes(apiExecuteTimes);
        ydOpenapiLog.setCreateTime(new Date());
        ydOpenapiLog.setClientIp(RequestUtils.getIpAddr(SpringWebUtils.getRequest()));
        save(ydOpenapiLog);
    }
}
