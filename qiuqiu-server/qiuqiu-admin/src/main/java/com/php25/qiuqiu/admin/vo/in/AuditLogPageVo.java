package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Mod11Check;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

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
    @Positive
    private Integer pageNum;

    /**
     * 每页记录数
     */
    @Positive
    @Max(100)
    private Integer pageSize;
}
