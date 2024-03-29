package com.php25.qiuqiu.admin.vo.in.resource;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/27 09:23
 */
@Setter
@Getter
public class ResourceIdVo {

    /**
     * 资源名
     */
    @NotBlank
    private String name;
}
