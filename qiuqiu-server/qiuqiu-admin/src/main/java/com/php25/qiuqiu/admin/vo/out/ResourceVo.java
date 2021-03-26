package com.php25.qiuqiu.admin.vo.out;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/26 14:22
 */
@Setter
@Getter
public class ResourceVo {

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
}
