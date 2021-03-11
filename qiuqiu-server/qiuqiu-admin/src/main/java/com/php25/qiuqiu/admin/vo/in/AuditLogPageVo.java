package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/11 15:26
 */
@Setter
@Getter
public class AuditLogPageVo {

    /**
     * 页面
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;
}
