package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.model.PermissionRef;
import com.php25.qiuqiu.user.model.Role;

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

    /**
     * 根据角色id获取对应的权限ids
     *
     * @param roleId 角色id
     * @return 权限ids;
     */
    List<Long> getPermissionIdsByRoleId(Long roleId);

    /**
     * 创建角色与权限关系
     *
     * @param permissionRefs 角色与权限关系
     * @return true:成功
     */
    boolean createPermissionRefs(List<PermissionRef> permissionRefs);


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
