package com.php25.qiuqiu.user.constant;

import com.php25.common.core.exception.BusinessErrorStatus;

/**
 * @author penghuiping
 * @date 2021/2/3 13:41
 */
public enum UserErrorCode implements BusinessErrorStatus {
    USER_NOT_FOUND("00000", "用户不存在"),
    JWT_ILLEGAL("00001", "token不合法"),
    JWT_NOT_FIND("00002", "token缺失"),
    HAS_NO_PERMISSION("00004", "没有此接口权限");


    String code;
    String desc;


    UserErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
