package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/9 15:01
 */
@Setter
@Getter
public class RoleUpdateVo {

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
     * false:无效 true:有效
     */
    private Boolean enable;
}
