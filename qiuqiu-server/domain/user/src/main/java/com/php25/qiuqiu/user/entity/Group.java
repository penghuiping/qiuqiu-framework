package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 组织机构部门
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
public class Group  {

    /**
     * id,自增
     */
    private Long id;

    /**
     * 组名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 是否有效 0:无效 1:有效
     */
    private Boolean enable;
}
