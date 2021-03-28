package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
