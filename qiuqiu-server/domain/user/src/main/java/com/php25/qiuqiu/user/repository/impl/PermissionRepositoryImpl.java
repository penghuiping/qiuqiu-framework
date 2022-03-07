package com.php25.qiuqiu.user.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.user.dao.PermissionDao;
import com.php25.qiuqiu.user.dao.ResourcePermissionDao;
import com.php25.qiuqiu.user.dao.po.PermissionPo;
import com.php25.qiuqiu.user.dao.po.ResourcePermissionPo;
import com.php25.qiuqiu.user.entity.Permission;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/5 17:41
 */
@RequiredArgsConstructor
@Component
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionDao permissionDao;
    private final ResourcePermissionDao resourcePermissionDao;

    @Override
    public Boolean hasReferencedByResource(String permissionName) {
        LambdaQueryWrapper<ResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ResourcePermissionPo::getPermission, permissionName);
        return resourcePermissionDao.selectCount(lambdaQueryWrapper) > 0;
    }

    @Override
    public boolean save(Permission permission, boolean isInsert) {
        if (isInsert) {
            PermissionPo permissionPo = new PermissionPo();
            BeanUtils.copyProperties(permission, permissionPo);
            return permissionDao.insert(permissionPo) > 0;
        } else {
            PermissionPo permissionPo = new PermissionPo();
            BeanUtils.copyProperties(permission, permissionPo);
            return permissionDao.updateById(permissionPo) > 0;
        }
    }

    @Override
    public List<Permission> findAllEnabled() {
        List<PermissionPo> permissionPos = permissionDao.selectList(Wrappers.<PermissionPo>lambdaQuery().eq(PermissionPo::getEnable, true));
        if (null != permissionPos && !permissionPos.isEmpty()) {
            return permissionPos.stream().map(permissionPo -> {
                Permission permission = new Permission();
                BeanUtils.copyProperties(permissionPo, permission);
                return permission;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public IPage<Permission> page(String permissionName, Integer pageNum, Integer pageSize) {
        IPage<PermissionPo> page = permissionDao
                .selectPage(new Page<>(pageNum, pageSize),
                        Wrappers.<PermissionPo>lambdaQuery()
                                .eq(StringUtil.isNotBlank(permissionName),PermissionPo::getName, permissionName));
        List<Permission> permissions = new ArrayList<>();
        if (null != page.getRecords() && !page.getRecords().isEmpty()) {
            permissions = page.getRecords().stream().map(permissionPo -> {
                Permission permission = new Permission();
                BeanUtils.copyProperties(permissionPo, permission);
                return permission;
            }).collect(Collectors.toList());
        }
        IPage<Permission> result = new Page<>();
        result.setRecords(permissions);
        result.setTotal(page.getTotal());
        result.setCurrent(pageNum);
        result.setSize(pageSize);
        return result;
    }

    @Override
    public boolean delete(Permission permission) {
        return permissionDao.deleteById(permission.getName()) > 0;
    }
}
