package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/25 16:16
 */
@Setter
@Getter
public class Resource {

    /**
     * 资源名
     */
    private String name;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 是否有效 0:无效 1:有效
     */
    private Boolean enable;
}
