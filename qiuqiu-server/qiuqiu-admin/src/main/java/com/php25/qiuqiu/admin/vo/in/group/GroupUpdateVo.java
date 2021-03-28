package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

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
    private Long id;

    /**
     * 组名
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
