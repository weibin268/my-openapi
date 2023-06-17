package com.zhuang.openapi.pojo.log;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * OpenApi调用日志
 * </p>
 *
 * @author admin，IP：10.147.20.136、172.19.64.1、172.21.64.1、172.24.128.1、172.25.64.1、172.28.64.1、172.29.32.1、172.30.208.1、192.168.1.4、192.168.6.1、192.168.73.1
 * @since 2023-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "YdOpenapiLog对象", description = "OpenApi调用日志")
public class SysOpenapiLog implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "OpenApi用户ID")
    @TableField("openapi_user_id")
    private String openapiUserId;

    @ApiModelProperty(value = "api名字")
    @TableField("api_name")
    private String apiName;

    @ApiModelProperty(value = "api参数值")
    @TableField("api_params")
    private String apiParams;

    @ApiModelProperty(value = "接口执行时间（单位：毫秒）")
    @TableField("api_execute_times")
    private Integer apiExecuteTimes;

    @ApiModelProperty(value = "客户端IP")
    @TableField("client_ip")
    private String clientIp;


    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;


}
