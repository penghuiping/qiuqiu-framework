package com.php25.qiuqiu.admin.vo.in.permission;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/9 17:31
 */
@Setter
@Getter
public class PermissionCreateVo {
    /**
     * 权限名
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;
}
