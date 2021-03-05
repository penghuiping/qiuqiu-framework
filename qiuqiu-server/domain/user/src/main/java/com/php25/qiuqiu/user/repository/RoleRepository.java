package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.repository.model.Role;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 16:54
 */
public interface RoleRepository extends BaseDbRepository<Role, Long> {

    /**
     * 根据角色id获取对应的权限ids
     *
     * @param roleIds 角色ids
     * @return 权限ids
     */
    List<Long> getPermissionIdsByRoleIds(List<Long> roleIds);
}
