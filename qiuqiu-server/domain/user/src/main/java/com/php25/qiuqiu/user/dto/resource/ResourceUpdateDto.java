package com.php25.qiuqiu.user.dto.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/25 17:17
 */
@Setter
@Getter
public class ResourceUpdateDto {

    private String name;

    private String description;

    private List<ResourcePermissionDto> resourcePermissions;

    private Boolean enable;
}
