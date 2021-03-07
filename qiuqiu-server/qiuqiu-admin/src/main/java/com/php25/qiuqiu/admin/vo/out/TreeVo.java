package com.php25.qiuqiu.admin.vo.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 22:07
 */
@Setter
@Getter
public class TreeVo {
    /**
     * 节点id
     */
    private String id;

    /**
     * 节点值
     */
    private String value;

    /**
     * 节点名
     */
    private String label;

    /**
     * 子节点列表
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TreeVo> children;

    /**
     * 是否可用
     */
    private Boolean disabled;
}
