package com.php25.qiuqiu.user.constant;

import com.php25.common.core.exception.BusinessErrorStatus;

/**
 * @author penghuiping
 * @date 2021/2/3 13:41
 */
public enum UserErrorCode implements BusinessErrorStatus {
    USER_NOT_FOUND("10000", "用户不存在"),
    JWT_ILLEGAL("10001", "token不合法"),
    JWT_NOT_FIND("10002", "token缺失"),
    HAS_NO_PERMISSION("10004", "没有此接口权限"),
    ROLE_HAS_BEEN_REFERENCED_BY_USER("10005", "角色被用户所关联,无法删除"),
    PERMISSION_HAS_BEEN_REFERENCED_BY_ROLE("10006", "权限被角色所关联,无法删除"),
    GROUP_HAS_BEEN_REFERENCED_BY_USER("10007", "组被用户所关联,无法删除"),
    GROUP_HAS_BEEN_REFERENCED_BY_GROUP("10008", "组被其他组所关联,无法删除");


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
