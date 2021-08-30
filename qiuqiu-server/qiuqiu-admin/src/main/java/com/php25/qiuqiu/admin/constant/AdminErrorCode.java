package com.php25.qiuqiu.admin.constant;

import com.php25.common.core.exception.BusinessErrorStatus;

/**
 * @author penghuiping
 * @date 2021/3/8 20:07
 */
public enum AdminErrorCode implements BusinessErrorStatus {
    IMAGE_VALIDATION_CODE_ERROR("A1000", "图形验证码输入错误");

    AdminErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    private String description;


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
