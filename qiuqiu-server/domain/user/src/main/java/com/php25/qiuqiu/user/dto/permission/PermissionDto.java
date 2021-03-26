package com.php25.qiuqiu.user.dto.permission;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/1 21:05
 */
@Setter
@Getter
@EqualsAndHashCode
public class PermissionDto {
    private String name;

    @EqualsAndHashCode.Exclude
    private String description;

    private Boolean enable;
}
