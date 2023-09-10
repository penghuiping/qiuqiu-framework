package com.php25.qiuqiu.admin.vo.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/11 15:26
 */
@Setter
@Getter
public class SystemLogPageVo {

    /**
     * 搜索关键字
     */
    private String keywords;

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
