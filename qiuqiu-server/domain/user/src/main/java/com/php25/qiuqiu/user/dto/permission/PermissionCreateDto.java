package com.php25.qiuqiu.user.dto.permission;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/6 10:58
 */
@Setter
@Getter
public class PermissionCreateDto {
    private String name;

    private String description;

    private String uri;
}
