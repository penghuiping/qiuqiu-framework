package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
