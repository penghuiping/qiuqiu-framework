package com.php25.qiuqiu.admin.vo.out.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/26 13:28
 */
@Tag(name = "资源权限",description = "资源权限")
@Setter
@Getter
public class ResourcePermissionVo {

    /**
     * 资源
     */
    @Schema(description = "资源")
    private String resource;

    /**
     * 资源对应的权限
     */
    @Schema(description = "资源对应的权限")
    private List<String> permissions;
}
