package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
public class Role  {

    /**
     * id,自增
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否可用 0:不可用 1:可用
     */
    private Boolean enable;
}
