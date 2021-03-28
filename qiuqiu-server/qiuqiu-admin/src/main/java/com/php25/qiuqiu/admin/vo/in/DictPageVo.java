package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
