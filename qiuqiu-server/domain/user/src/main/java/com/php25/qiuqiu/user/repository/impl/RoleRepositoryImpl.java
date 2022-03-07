package com.php25.qiuqiu.user.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.user.dao.RoleDao;
import com.php25.qiuqiu.user.dao.RoleResourcePermissionDao;
import com.php25.qiuqiu.user.dao.po.RolePo;
import com.php25.qiuqiu.user.dao.po.RoleResourcePermissionPo;
import com.php25.qiuqiu.user.entity.Role;
import com.php25.qiuqiu.user.entity.RoleResourcePermission;
import com.php25.qiuqiu.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/5 16:55
 */
@RequiredArgsConstructor
@Component
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleResourcePermissionDao roleResourcePermissionDao;

    private final RoleDao roleDao;

    @Override
    public List<RoleResourcePermission> getPermissionsByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RoleResourcePermissionPo::getRoleId, roleId).select();
        List<RoleResourcePermissionPo> roleResourcePermissionPos = roleResourcePermissionDao.selectList(lambdaQueryWrapper);
        if (null != roleResourcePermissionPos && !roleResourcePermissionPos.isEmpty()) {
            return roleResourcePermissionPos.stream().map(roleResourcePermissionPo -> {
                RoleResourcePermission roleResourcePermission = new RoleResourcePermission();
                BeanUtils.copyProperties(roleResourcePermissionPo, roleResourcePermission);
                return roleResourcePermission;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public List<RoleResourcePermission> getPermissionsByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RoleResourcePermissionPo::getRoleId, roleIds).select();
        List<RoleResourcePermissionPo> roleResourcePermissionPos = roleResourcePermissionDao
                .selectList(lambdaQueryWrapper);
        if (null != roleResourcePermissionPos && !roleResourcePermissionPos.isEmpty()) {
            return roleResourcePermissionPos.stream().map(roleResourcePermissionPo -> {
                RoleResourcePermission roleResourcePermission = new RoleResourcePermission();
                BeanUtils.copyProperties(roleResourcePermissionPo, roleResourcePermission);
                return roleResourcePermission;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public boolean createPermissionRefs(List<RoleResourcePermission> permissionRefs) {
        if (null != permissionRefs && !permissionRefs.isEmpty()) {
            List<RoleResourcePermissionPo> resourcePermissionPos = permissionRefs.stream()
                    .map(roleResourcePermission -> {
                        RoleResourcePermissionPo roleResourcePermissionPo = new RoleResourcePermissionPo();
                        BeanUtils.copyProperties(roleResourcePermission, roleResourcePermissionPo);
                        return roleResourcePermissionPo;
                    }).collect(Collectors.toList());
            roleResourcePermissionDao.insertBatch(resourcePermissionPos);
        }
        return true;
    }

    @Override
    public boolean deletePermissionRefsByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RoleResourcePermissionPo::getRoleId, roleId).select();
        roleResourcePermissionDao.delete(lambdaQueryWrapper);
        return true;
    }

    @Override
    public boolean deletePermissionRefsByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RoleResourcePermissionPo::getRoleId, roleIds).select();
        roleResourcePermissionDao.delete(lambdaQueryWrapper);
        return true;
    }

    @Override
    public Optional<Role> findById(Long id) {
        RolePo rolePo = roleDao.selectById(id);
        if (null != rolePo) {
            Role role = new Role();
            BeanUtils.copyProperties(rolePo, role);
            return Optional.of(role);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Role role, boolean isInsert) {
        RolePo rolePo = new RolePo();
        BeanUtils.copyProperties(role, rolePo);
        if (isInsert) {
            //新增
            return roleDao.insert(rolePo) > 0;
        } else {
            //更新
            return roleDao.updateById(rolePo) > 0;
        }
    }

    @Override
    public List<Role> findAllById(List<Long> ids) {
        List<RolePo> rolePos = roleDao.selectBatchIds(ids);
        if (null != rolePos && !rolePos.isEmpty()) {
            return rolePos.stream().map(rolePo -> {
                Role role = new Role();
                BeanUtils.copyProperties(rolePo, role);
                return role;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public List<Role> findAllEnabled() {
        List<RolePo> rolePos = roleDao.selectList(Wrappers.<RolePo>lambdaQuery().eq(RolePo::getEnable, true));
        if (null != rolePos && !rolePos.isEmpty()) {
            return rolePos.stream().map(rolePo -> {
                Role role = new Role();
                BeanUtils.copyProperties(rolePo, role);
                return role;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public boolean deleteAll(List<Role> roles) {
        List<Long> ids = roles.stream().map(Role::getId).collect(Collectors.toList());
        return roleDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public IPage<Role> page(String roleName, Integer pageNum, Integer pageSize) {
        IPage<RolePo> page = roleDao
                .selectPage(new Page<>(pageNum, pageSize),
                        Wrappers.<RolePo>lambdaQuery().eq(StringUtil.isNotBlank(roleName),RolePo::getName, roleName));
        Page<Role> rolePage = new Page<>();
        rolePage.setRecords(page.getRecords().stream().map(rolePo -> {
            Role role = new Role();
            BeanUtils.copyProperties(rolePo, role);
            return role;
        }).collect(Collectors.toList()));
        rolePage.setCurrent(pageNum);
        rolePage.setTotal(page.getTotal());
        rolePage.setSize(pageSize);
        return rolePage;
    }
}
