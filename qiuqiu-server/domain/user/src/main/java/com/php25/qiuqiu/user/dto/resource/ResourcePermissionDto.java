package com.php25.qiuqiu.user.dto.resource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/25 17:13
 */
@Setter
@Getter
@EqualsAndHashCode
public class ResourcePermissionDto {

    /**
     * 资源id
     */
    private String resource;

    /**
     * 权限id
     */
    private String permission;
}
