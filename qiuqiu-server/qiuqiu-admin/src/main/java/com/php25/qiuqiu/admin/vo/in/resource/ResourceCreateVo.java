package com.php25.qiuqiu.admin.vo.in.resource;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/26 14:00
 */
@Setter
@Getter
public class ResourceCreateVo {

    /***
     * 资源名
     */
    private String name;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 资源对应的权限操作
     */
    private List<String> permissions;
}
