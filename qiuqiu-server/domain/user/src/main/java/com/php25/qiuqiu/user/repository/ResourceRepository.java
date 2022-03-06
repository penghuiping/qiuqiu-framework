package com.php25.qiuqiu.user.repository;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.user.entity.Resource;
import com.php25.qiuqiu.user.entity.ResourcePermission;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/25 20:12
 */
public interface ResourceRepository {

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

    /**
     * 保存
     *
     * @param resource 资源
     * @param isInsert true:新增 false:更新
     * @return true:成功
     */
    boolean save(Resource resource, boolean isInsert);

    /**
     * 获取所有有效的资源
     *
     * @return 资源列表
     */
    List<Resource> findAllEnabled();

    /**
     * 根据资源id获取资源
     *
     * @param id 资源id
     * @return 资源
     */
    Optional<Resource> findById(String id);

    /**
     * 物理删除资源
     *
     * @param id 主键id
     * @return true:删除成功
     */
    boolean deleteById(String id);

    /**
     * 分页
     *
     * @param resourceName 资源名
     * @param pageNum      页面
     * @param pageSize     页记录数
     * @return 分页数据
     */
    IPage<Resource> page(String resourceName, Integer pageNum, Integer pageSize);
}
