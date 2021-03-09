package com.php25.qiuqiu.user.dto.role;


import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/6 17:17
 */
@Setter
@Getter
public class RolePageDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 0:无效 1:有效
     */
    private Boolean enable;
}
