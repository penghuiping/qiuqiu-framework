package com.php25.common.web;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author penghuiping
 * @date 2019/7/19 09:51
 */
public abstract class BaseResponse<T> {
    /**
     * 错误码
     */
    @ApiModelProperty("错误码,00000:成功,非零:表示有错误")
    private String code;

    /**
     * 接口返回数据
     */
    @ApiModelProperty("接口返回数据")
    private T data;

    /**
     * 对应错误码的提示信息
     */
    @ApiModelProperty("对应错误码的提示信息")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
