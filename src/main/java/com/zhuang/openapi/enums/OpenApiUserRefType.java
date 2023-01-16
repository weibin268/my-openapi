package com.zhuang.openapi.enums;

import java.util.Arrays;

/**
 * OpenApi用户关联对象类型
 */
public enum OpenApiUserRefType {

    IrrBSRole("IrrBSRole", "id", "角色"),
    yd_project("yd_project", "prjcode", "项目"),
    yd_mp_station("yd_mp_station", "rtucode", "测站"),
    yd_monitor_point("yd_monitor_point", "mpcode", "测点");

    OpenApiUserRefType(String refTable, String refId, String remark) {
        this.refTable = refTable;
        this.refId = refId;
        this.remark = remark;
    }

    private String refTable;
    private String refId;
    private String remark;

    public String getRefTable() {
        return refTable;
    }

    public String getRefId() {
        return refId;
    }

    public String getRemark() {
        return remark;
    }

    public OpenApiUserRefType getByRefTable(String refTable) {
        return Arrays.stream(OpenApiUserRefType.values()).filter(c -> c.getRefTable().equals(refTable)).findFirst().orElse(null);
    }
}
