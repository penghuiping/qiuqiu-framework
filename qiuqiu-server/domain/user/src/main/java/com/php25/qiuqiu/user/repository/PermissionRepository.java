package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.model.Permission;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 17:40
 */
public interface PermissionRepository extends BaseDbRepository<Permission, String> {


    /**
     * 判断权限是否被资源所引用
     *
     * @param permissionName 权限名
     * @return true:被引用
     */
    Boolean hasReferencedByResource(String permissionName);
}
