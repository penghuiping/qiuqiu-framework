package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.entity.Role;
import com.php25.qiuqiu.user.entity.RoleResourcePermission;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 16:54
 */
public interface RoleRepository extends BaseDbRepository<Role, Long> {

    /**
     * 角色权限关系
     *
     * @param roleId 角色id
     */
    List<RoleResourcePermission> getPermissionsByRoleId(Long roleId);

    /**
     * 角色权限关系(可能有重复)
     *
     * @param roleIds 角色id列表
     * @return 角色权限关系
     */
    List<RoleResourcePermission> getPermissionsByRoleIds(List<Long> roleIds);

    /**
     * 创建角色与权限关系
     *
     * @param permissionRefs 角色与权限关系
     * @return true:成功
     */
    boolean createPermissionRefs(List<RoleResourcePermission> permissionRefs);


    /**
     * 删除角色与权限关系
     *
     * @param roleId 角色id
     * @return true:成功
     */
    boolean deletePermissionRefsByRoleId(Long roleId);

    /**
     * 删除角色与权限关系
     *
     * @param roleIds 角色id列表
     * @return true:成功
     */
    boolean deletePermissionRefsByRoleIds(List<Long> roleIds);
}
