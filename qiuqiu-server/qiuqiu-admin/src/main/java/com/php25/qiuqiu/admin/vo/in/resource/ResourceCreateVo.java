package com.php25.qiuqiu.admin.vo.in.resource;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotBlank
    private String name;

    /**
     * 资源描述
     */
    @NotBlank
    private String description;

    /**
     * 资源对应的权限操作
     */
    @Size(min = 1)
    private List<String> permissions;
}
