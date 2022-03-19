package com.php25.qiuqiu.user.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.user.dao.ResourceDao;
import com.php25.qiuqiu.user.dao.ResourcePermissionDao;
import com.php25.qiuqiu.user.dao.po.ResourcePermissionPo;
import com.php25.qiuqiu.user.dao.po.ResourcePo;
import com.php25.qiuqiu.user.entity.Resource;
import com.php25.qiuqiu.user.entity.ResourcePermission;
import com.php25.qiuqiu.user.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/25 21:03
 */
@RequiredArgsConstructor
@Repository
public class ResourceRepositoryImpl implements ResourceRepository {
    private final ResourcePermissionDao resourcePermissionDao;
    private final ResourceDao resourceDao;


    @Override
    public boolean deleteResourcePermissions(String resourceName) {
        LambdaQueryWrapper<ResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ResourcePermissionPo::getResource, resourceName);
        return resourcePermissionDao.delete(lambdaQueryWrapper) > 0;
    }

    @Override
    public boolean createResourcePermissions(List<ResourcePermission> resourcePermissions) {
        if (null != resourcePermissions && !resourcePermissions.isEmpty()) {
            List<ResourcePermissionPo> resourcePermissionPos = resourcePermissions.stream()
                    .map(resourcePermission -> {
                        ResourcePermissionPo resourcePermissionPo = new ResourcePermissionPo();
                        BeanUtils.copyProperties(resourcePermission, resourcePermissionPo);
                        return resourcePermissionPo;
                    }).collect(Collectors.toList());
            resourcePermissionDao.insertBatch(resourcePermissionPos);
        }
        return true;
    }

    @Override
    public boolean hasReferencedByRole(String resourceName) {
        LambdaQueryWrapper<ResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ResourcePermissionPo::getResource, resourceName);
        return resourcePermissionDao.selectCount(lambdaQueryWrapper) > 0;
    }

    @Override
    public List<ResourcePermission> getAllResourcePermissions() {
        LambdaQueryWrapper<ResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<ResourcePermissionPo> resourcePermissionPos = resourcePermissionDao.selectList(lambdaQueryWrapper);
        if (null != resourcePermissionPos && !resourcePermissionPos.isEmpty()) {
            return resourcePermissionPos.stream().map(resourcePermissionPo -> {
                ResourcePermission resourcePermission = new ResourcePermission();
                BeanUtils.copyProperties(resourcePermissionPo, resourcePermission);
                return resourcePermission;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public List<ResourcePermission> getResourcePermissionsByResourceName(String resourceName) {
        LambdaQueryWrapper<ResourcePermissionPo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ResourcePermissionPo::getResource, resourceName);
        List<ResourcePermissionPo> resourcePermissionPos = resourcePermissionDao.selectList(lambdaQueryWrapper);
        if (null != resourcePermissionPos && !resourcePermissionPos.isEmpty()) {
            return resourcePermissionPos.stream().map(resourcePermissionPo -> {
                ResourcePermission resourcePermission = new ResourcePermission();
                BeanUtils.copyProperties(resourcePermissionPo, resourcePermission);
                return resourcePermission;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public boolean save(Resource resource, boolean isInsert) {
        ResourcePo resourcePo = new ResourcePo();
        if (isInsert) {
            BeanUtils.copyProperties(resource, resourcePo);
            int res = resourceDao.insert(resourcePo);
            return res>0;
        } else {
            BeanUtils.copyProperties(resource, resourcePo);
            return resourceDao.updateById(resourcePo) > 0;
        }
    }

    @Override
    public List<Resource> findAllEnabled() {
        List<ResourcePo> resources = resourceDao.selectList(Wrappers.<ResourcePo>lambdaQuery().eq(ResourcePo::getEnable, true));
        if (null != resources && !resources.isEmpty()) {
            return resources.stream().map(resourcePo -> {
                Resource resource = new Resource();
                BeanUtils.copyProperties(resourcePo, resource);
                return resource;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    @Override
    public Optional<Resource> findById(String id) {
        ResourcePo resourcePo = resourceDao.selectById(id);
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourcePo, resource);
        return Optional.of(resource);
    }

    @Override
    public boolean deleteById(String id) {
        return resourceDao.deleteById(id) > 0;
    }

    @Override
    public IPage<Resource> page(String resourceName, Integer pageNum, Integer pageSize) {
        IPage<ResourcePo> iPage = resourceDao
                .selectPage(new Page<>(pageNum, pageSize),
                        Wrappers.<ResourcePo>lambdaQuery()
                                .eq(StringUtil.isNotBlank(resourceName),ResourcePo::getName, resourceName));
        List<ResourcePo> resourcePos = iPage.getRecords();
        List<Resource> resources = resourcePos.stream().map(resourcePo -> {
            Resource resource = new Resource();
            BeanUtils.copyProperties(resourcePo, resource);
            return resource;
        }).collect(Collectors.toList());
        IPage<Resource> resourcePage = new Page<>();
        resourcePage.setCurrent(pageNum);
        resourcePage.setSize(pageSize);
        resourcePage.setTotal(iPage.getTotal());
        resourcePage.setRecords(resources);
        return resourcePage;
    }
}
