package com.php25.qiuqiu.user.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.user.entity.Permission;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 17:40
 */
public interface PermissionRepository {


    /**
     * 判断权限是否被资源所引用
     *
     * @param permissionName 权限名
     * @return true:被引用
     */
    Boolean hasReferencedByResource(String permissionName);


    /**
     * 保存
     *
     * @param permission 权限
     * @param isInsert   true:新增 false:更新
     * @return true:成功
     */
    boolean save(Permission permission, boolean isInsert);

    /**
     * 获取所有有效的权限
     *
     * @return 权限列表
     */
    List<Permission> findAllEnabled();

    /**
     * 分页
     *
     * @param permissionName 权限名
     * @param pageNum        页面
     * @param pageSize       页记录数
     * @return 分页数据
     */
    IPage<Permission> page(String permissionName, Integer pageNum, Integer pageSize);

    /**
     * 物理删除权限
     *
     * @param permission 权限
     * @return true:成功
     */
    boolean delete(Permission permission);
}
