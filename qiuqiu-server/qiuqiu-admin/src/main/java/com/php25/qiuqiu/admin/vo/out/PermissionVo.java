package com.php25.qiuqiu.admin.vo.out;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限
 *
 * @author penghuiping
 * @date 2021/3/9 13:58
 */
@Setter
@Getter
public class PermissionVo {

    /**
     * 权限id
     */
    private Long id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限对应的接口uri地址
     */
    private String uri;
}
