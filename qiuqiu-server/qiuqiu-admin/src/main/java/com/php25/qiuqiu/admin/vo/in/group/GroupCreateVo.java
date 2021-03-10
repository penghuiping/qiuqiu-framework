package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

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
    private Long parentId;

    /**
     * 组名
     */
    private String name;

    /**
     * 组描述
     */
    private String description;
}
