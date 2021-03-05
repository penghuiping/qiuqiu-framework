package com.php25.qiuqiu.user.repository.model;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色与权限关系
 *
 * @author penghuiping
 * @date 2021/3/2 09:15
 */
@Setter
@Getter
@Table("t_role_permission")
public class PermissionRef {

    @Column("role_id")
    private Long roleId;

    @Column("permission_id")
    private Long permissionId;
}
