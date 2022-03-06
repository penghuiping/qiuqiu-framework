package com.php25.qiuqiu.user.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.user.entity.Role;
import com.php25.qiuqiu.user.entity.RoleResourcePermission;
import com.php25.qiuqiu.user.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/5 16:54
 */
public interface RoleRepository {

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

    /**
     * 获取角色
     *
     * @param id 角色id
     * @return 角色
     */
    Optional<Role> findById(Long id);

    /**
     * 保存
     *
     * @param role     角色
     * @param isInsert true:新增 false:更新
     * @return true:成功
     */
    boolean save(Role role, boolean isInsert);

    /**
     * 根据roleIds获取角色列表
     *
     * @param ids 角色ids
     * @return 角色列表
     */
    List<Role> findAllById(List<Long> ids);

    /**
     * 获取所有有效的角色
     *
     * @return 角色列表
     */
    List<Role> findAllEnabled();

    /**
     * 删除对应的角色
     *
     * @param roles 角色列表
     * @return true:删除成功
     */
    boolean deleteAll(List<Role> roles);

    /**
     * 分页
     *
     * @param roleName 角色名
     * @param pageNum  页面
     * @param pageSize 页记录数
     * @return 分页数据
     */
    IPage<Role> page(String roleName, Integer pageNum, Integer pageSize);
}
