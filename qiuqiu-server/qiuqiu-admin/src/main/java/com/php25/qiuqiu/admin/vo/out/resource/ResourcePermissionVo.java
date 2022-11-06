package com.php25.qiuqiu.admin.vo.out.resource;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/26 13:28
 */
@Setter
@Getter
public class ResourcePermissionVo {

    /**
     * 资源
     */
    @ApiModelProperty("资源")
    private String resource;

    /**
     * 资源对应的权限
     */
    @ApiModelProperty("资源对应的权限")
    private List<String> permissions;
}
