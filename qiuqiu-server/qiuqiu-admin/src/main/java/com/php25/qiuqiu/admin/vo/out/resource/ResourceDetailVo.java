package com.php25.qiuqiu.admin.vo.out.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/27 09:49
 */
@Setter
@Getter
public class ResourceDetailVo {

    /**
     * 资源名
     */
    private String name;

    /**
     * 资源描述
     */
    private String description;

    /**
     * true:有效 false:无效
     */
    private Boolean enable;

    /**
     * 权限列表
     */
    private List<String> permissions;
}
