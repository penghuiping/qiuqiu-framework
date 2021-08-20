package com.php25.qiuqiu.job.constant;

import com.php25.common.core.exception.BusinessErrorStatus;

/**
 * @author penghuiping
 * @date 2021/7/26 16:05
 */
public enum JobErrorCode implements BusinessErrorStatus {
    JOB_EXECUTION_HAS_LOADED_CANT_DELETE("30000","执行任务已经被加载,不能被删除");

    private String code;
    private String description;


    JobErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.description;
    }
}
