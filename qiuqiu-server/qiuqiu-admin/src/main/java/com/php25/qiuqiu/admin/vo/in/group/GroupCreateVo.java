package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/10 15:59
 */
@Setter
@Getter
public class GroupCreateVo {

    /**
     * 父组id
     */
    @NotNull
    @Positive
    private Long parentId;

    /**
     * 组名
     */
    @NotBlank
    private String name;

    /**
     * 组描述
     */
    @NotBlank
    private String description;
}
