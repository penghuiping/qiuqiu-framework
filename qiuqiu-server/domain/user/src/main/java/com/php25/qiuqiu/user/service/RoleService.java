package com.php25.qiuqiu.user.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.ResourcePermission0Dto;
import com.php25.qiuqiu.user.dto.role.RoleCreateDto;
import com.php25.qiuqiu.user.dto.role.RoleDetailDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.role.RolePageDto;
import com.php25.qiuqiu.user.dto.role.RoleUpdateDto;

import java.util.List;
import java.util.Set;

/**
 * @author penghuiping
 * @date 2021/3/5 21:43
 */
public interface RoleService {

    /**
     * 创建角色
     *
     * @param role 角色
     * @return true:创建成功
     */
    Boolean create(RoleCreateDto role);

    /**
     * 更新角色
     *
     * @param role 角色
     * @return true: 更新成功
     */
    Boolean update(RoleUpdateDto role);

    /**
     * 物理删除角色
     *
     * @param roleIds 角色id列表
     * @return true:删除成功
     */
    Boolean delete(List<Long> roleIds);

    /**
     * 角色列表分页查询
     *
     * @param roleName 角色名
     * @param pageNum  当前页码
     * @param pageSize 每页记录数
     * @return 列表数据
     */
    DataGridPageDto<RolePageDto> page(String roleName, Integer pageNum, Integer pageSize);


    /**
     * 获取系统中所有的有效角色
     *
     * @return 角色列表
     */
    List<RoleDto> getAllRoles();

    /**
     * 根据角色名获取权限列表
     *
     * @param roleName 角色名
     * @return 此角色名对应的权限列表
     */
    Set<ResourcePermissionDto> getPermissionsByRoleName(String roleName);


    /**
     * 获取系统中所有角色详情
     *
     * @param roleId 角色id
     * @return 角色详情信息
     */
    RoleDetailDto detail(Long roleId);

}
