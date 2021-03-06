package com.php25.qiuqiu.user.dto.group;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/6 11:09
 */
@Setter
@Getter
public class GroupCreateDto {

    /**
     * 组织名
     */
    private String name;

    /**
     * 组织描述
     */
    private String description;

    /**
     * 父组织id
     */
    private Long parentId;
}
