package com.php25.qiuqiu.admin.vo.in.resource;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(min = 1)
    private List<String> permissions;
}
