package com.php25.qiuqiu.user.model;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 资源与权限关系
 *
 * @author penghuiping
 * @date 2021/3/2 09:15
 */
@Setter
@Getter
@Table("t_resource_permission")
public class ResourcePermission {

    /**
     * 资源id
     */
    @Column("resource")
    private String resource;

    /**
     * 权限id
     */
    @Column("permission")
    private String permission;
}
