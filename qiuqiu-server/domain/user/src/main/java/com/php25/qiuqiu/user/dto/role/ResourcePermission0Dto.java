package com.php25.qiuqiu.user.dto.role;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/25 21:42
 */
@Setter
@Getter
@EqualsAndHashCode
public class ResourcePermission0Dto {

    /**
     * 资源id
     */
    private String resource;

    /**
     * 权限id
     */
    private String permission;
}
