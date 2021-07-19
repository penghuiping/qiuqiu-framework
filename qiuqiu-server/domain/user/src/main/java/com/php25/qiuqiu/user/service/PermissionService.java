package com.php25.qiuqiu.user.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:54
 */
public interface PermissionService {


    /**
     * 创建权限
     *
     * @param permission 权限
     * @return true:创建成功
     */
    Boolean create(PermissionCreateDto permission);

    /**
     * 更新权限
     *
     * @param permission 权限
     * @return true:更新成功
     */
    Boolean update(PermissionDto permission);

    /**
     * 物理删除权限
     *
     * @param permissionName 权限名
     * @return true:删除成功
     */
    Boolean delete(String permissionName);

    /**
     * 权限列表分页查询
     *
     * @param permissionName 权限名搜索
     * @param pageNum        当前页面
     * @param pageSize       每页几条数据
     * @return 权限列表
     */
    DataGridPageDto<PermissionDto> page(String permissionName, Integer pageNum, Integer pageSize);

    /**
     * 获取所有有效的权限列表
     *
     * @return 所有有效的权限列表
     */
    List<PermissionDto> getAll();
}
