package com.php25.qiuqiu.admin.vo.out;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 22:25
 */
@Setter
@Getter
public class PageResultVo<T> {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 分页数据
     */
    private List<T> data;

    /**
     * 当前第几页
     */
    private Integer pageNum;
}
