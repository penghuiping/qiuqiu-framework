package com.php25.qiuqiu.admin.vo.out;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/9 13:45
 */
@Setter
@Getter
public class RoleDetailOutVo {

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
     * 权限id列表
     */
    private List<Long> permissionIds;

    /**
     * 权限名列表
     */
    private List<String> permissions;

    /**
     * 0:无效 1:有效
     */
    private Integer enable;
}
