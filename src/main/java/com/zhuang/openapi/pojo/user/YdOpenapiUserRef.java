package com.zhuang.openapi.pojo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;

/**
 * <p>
 * OpenApi用户资源关联表
 * </p>
 *
 * @author admin，IP：10.147.20.136、172.19.64.1、172.21.64.1、172.24.128.1、172.25.64.1、172.28.64.1、172.29.32.1、172.30.208.1、192.168.1.4、192.168.6.1、192.168.73.1
 * @since 2023-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "YdOpenapiUserRef对象", description = "OpenApi用户资源关联表")
public class YdOpenapiUserRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "OpenApi用户Id")
    @TableField("openapi_user_id")
    private String openapiUserId;

    @ApiModelProperty(value = "关联表名")
    @TableField("ref_table")
    private String refTable;

    @ApiModelProperty(value = "关联记录Id")
    @TableField("ref_id")
    private String refId;


}
