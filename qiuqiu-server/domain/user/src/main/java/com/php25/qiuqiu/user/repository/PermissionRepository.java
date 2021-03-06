package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.model.Permission;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 17:40
 */
public interface PermissionRepository extends BaseDbRepository<Permission, Long> {

    /**
     * 根据权限id列表获取角色列表
     *
     * @param permissionIds 权限id列表
     * @return 角色id列表
     */
    List<Long> getRoleIdsByPermissionIds(List<Long> permissionIds);
}
