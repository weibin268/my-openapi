package com.zhuang.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenApiUserInfo {
    @ApiModelProperty("用户Id")
    private String userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("关联信息列表")
    private List<RefInfo> refList = new ArrayList<>();

    @Data
    public static class RefInfo {
        @ApiModelProperty("关联表名：IrrBSRole=角色；yd_project=项目；yd_mp_station=测站；yd_monitor_point=测点；")
        private String refTable;
        @ApiModelProperty("关联Id")
        private String refId;
    }
}
