package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Mod11Check;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/11 15:26
 */
@Setter
@Getter
public class AuditLogPageVo {

    /**
     * 用户名搜索
     */
    private String username;

    /**
     * 页面
     */
    @NotNull
    @Positive
    private Integer pageNum;

    /**
     * 每页记录数
     */
    @NotNull
    @Positive
    @Max(100)
    private Integer pageSize;
}
