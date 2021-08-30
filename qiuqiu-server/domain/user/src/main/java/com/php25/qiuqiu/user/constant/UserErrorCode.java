package com.php25.qiuqiu.user.constant;

import com.php25.common.core.exception.BusinessErrorStatus;

/**
 * 用户模块错误码
 * @author penghuiping
 * @date 2021/2/3 13:41
 */
public enum UserErrorCode implements BusinessErrorStatus {
    USER_NOT_FOUND("A1100", "用户不存在"),
    JWT_ILLEGAL("A1101", "token不合法"),
    JWT_NOT_FIND("A1102", "token缺失"),
    JWT_EXPIRED("A1103", "token已过期"),
    HAS_NO_PERMISSION("A1104", "没有此接口权限"),
    ROLE_HAS_BEEN_REFERENCED_BY_USER("A1105", "角色被用户所关联,无法删除"),
    PERMISSION_HAS_BEEN_REFERENCED_BY_RESOURCE("A1106", "权限被资源所关联,无法删除"),
    GROUP_HAS_BEEN_REFERENCED_BY_USER("A1107", "组被用户所关联,无法删除"),
    GROUP_HAS_BEEN_REFERENCED_BY_GROUP("A1108", "组被其他组所关联,无法删除"),
    USER_DATA_NOT_EXISTS("A1109", "查询的用户数据不存在"),
    ROLE_DATA_NOT_EXISTS("A1110", "查询的角色数据不存在"),
    GROUP_DATA_NOT_EXISTS("A1111", "查询的组数据不存在"),
    PERMISSION_DATA_NOT_EXISTS("A1112", "查询的权限数据不存在"),
    RESOURCE_HAS_BEEN_REFERENCED_BY_ROLE("A1113", "资源被角色所引用无法删除"),
    RESOURCE_DATA_NOT_EXISTS("A1114", "查询的资源数据不存在"),
    REFRESH_TOKEN_ILLEGAL("A1115", "刷新token不合法,无法完成刷新操作");



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
