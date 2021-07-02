package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.entity.Resource;
import com.php25.qiuqiu.user.entity.ResourcePermission;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/25 20:12
 */
public interface ResourceRepository extends BaseDbRepository<Resource, String> {

    /**
     * 删除资源与权限关系
     *
     * @param resourceName 资源名
     * @return true:成功
     */
    boolean deleteResourcePermissions(String resourceName);

    /**
     * 批量新增资源与权限的关系
     *
     * @param resourcePermissions 资源与权限关系
     * @return true:新增成功
     */
    boolean createResourcePermissions(List<ResourcePermission> resourcePermissions);


    /**
     * 判断资源是否被角色所引用
     *
     * @param resourceName 资源名
     * @return true:被引用
     */
    boolean hasReferencedByRole(String resourceName);

    /**
     * 获取所有的资源与权限关系
     *
     * @return 资源与权限关系
     */
    List<ResourcePermission> getAllResourcePermissions();

    /**
     * 根据资源名获取资源权限列表
     *
     * @param resourceName 资源名
     * @return 资源权限列表
     */
    List<ResourcePermission> getResourcePermissionsByResourceName(String resourceName);
}
