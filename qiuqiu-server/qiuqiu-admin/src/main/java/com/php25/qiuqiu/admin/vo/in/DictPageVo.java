package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/11 16:51
 */
@Setter
@Getter
public class DictPageVo {

    /**
     * 通过key搜索
     */
    private String key;

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
