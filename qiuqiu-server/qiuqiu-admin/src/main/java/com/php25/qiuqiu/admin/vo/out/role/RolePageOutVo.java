package com.php25.qiuqiu.admin.vo.out.role;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/9 11:21
 */
@Setter
@Getter
public class RolePageOutVo {

    /**
     * 角色id
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
     * false:无效 true:有效
     */
    private Boolean enable;
}
