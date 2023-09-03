package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/28 09:55
 */
@Setter
@Getter
public class GroupUpdateVo {

    /**
     * 组id
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 组名
     */
    @NotBlank
    private String name;

    /**
     * 描述
     */
    @NotBlank
    private String description;
}
