package com.php25.qiuqiu.user.dto.group;

import com.php25.common.core.tree.TreeAble;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/1 21:05
 */
@Setter
@Getter
public class GroupDto implements TreeAble<Long> {

    /**
     * 组织id
     */
    private Long id;

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
