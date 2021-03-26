package com.php25.qiuqiu.user.dto.group;

import com.php25.common.core.tree.TreeAble;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/1 21:05
 */
@Setter
@Getter
@EqualsAndHashCode
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
    @EqualsAndHashCode.Exclude
    private String description;

    /**
     * 父组织id
     */
    @EqualsAndHashCode.Exclude
    private Long parentId;

}
