package com.php25.qiuqiu.user.entity;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户与角色关系
 *
 * @author penghuiping
 * @date 2021/3/2 09:06
 */
@Setter
@Getter
@Table("t_user_role")
public class UserRole {

    /**
     * 用户id
     */
    @Column("user_id")
    private Long userId;

    /**
     * 角色id
     */
    @Column("role_id")
    private Long roleId;
}
