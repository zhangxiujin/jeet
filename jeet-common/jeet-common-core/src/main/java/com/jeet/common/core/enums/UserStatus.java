package com.jeet.common.core.enums;

/**
 * @Desc: UserStatus
 * @author: qingmei
 * @Date 2023/8/3 10:35
 */
public enum UserStatus {

    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus (String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
