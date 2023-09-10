package com.php25.qiuqiu.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.resource.ResourceCreateDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDetailDto;
import com.php25.qiuqiu.user.dto.resource.ResourceDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.resource.ResourceUpdateDto;
import com.php25.qiuqiu.user.entity.Resource;
import com.php25.qiuqiu.user.entity.ResourcePermission;
import com.php25.qiuqiu.user.copyer.ResourceDtoCopyer;
import com.php25.qiuqiu.user.repository.ResourceRepository;
import com.php25.qiuqiu.user.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/25 20:11
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    private final ResourceDtoCopyer resourceDtoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(ResourceCreateDto resource) {
        Resource resource0 = resourceDtoMapper.toEntity(resource);
        resource0.setEnable(true);
        resourceRepository.save(resource0,true);
        List<ResourcePermissionDto> resourcePermissionDtos = resource.getResourcePermissions();
        if (null != resourcePermissionDtos && !resourcePermissionDtos.isEmpty()) {
            List<ResourcePermission> resourcePermissions = resourcePermissionDtos.stream().map(resourceDtoMapper::toEntity).collect(Collectors.toList());
            resourceRepository.createResourcePermissions(resourcePermissions);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(ResourceUpdateDto resource) {
        Resource resource0 = resourceDtoMapper.toEntity(resource);
        resourceRepository.save(resource0,false);

        List<ResourcePermissionDto> resourcePermissionDtos = resource.getResourcePermissions();
        if (null != resourcePermissionDtos && !resourcePermissionDtos.isEmpty()) {
            List<ResourcePermission> resourcePermissions = resourcePermissionDtos.stream().map(resourceDtoMapper::toEntity).collect(Collectors.toList());
            resourceRepository.deleteResourcePermissions(resource.getName());
            resourceRepository.createResourcePermissions(resourcePermissions);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String resourceName) {
        boolean hasReferenced = resourceRepository.hasReferencedByRole(resourceName);
        if (hasReferenced) {
            throw Exceptions.throwBusinessException(UserErrorCode.RESOURCE_HAS_BEEN_REFERENCED_BY_ROLE);
        }
        resourceRepository.deleteById(resourceName);
        resourceRepository.deleteResourcePermissions(resourceName);
        return true;
    }

    @Override
    public PageDto<ResourceDto> page(String resourceName, Integer pageNum, Integer pageSize) {
        IPage<Resource> page = this.resourceRepository.page(resourceName, pageNum,pageSize);
        PageDto<ResourceDto> dataGrid = new PageDto<>();
        List<ResourceDto> res = page.getRecords().stream().map(resourceDtoMapper::toDto).collect(Collectors.toList());
        dataGrid.setData(res);
        dataGrid.setTotal(page.getTotal());
        return dataGrid;
    }

    @Override
    public List<ResourceDetailDto> getAll() {
        List<ResourcePermission> resourcePermissions = this.resourceRepository.getAllResourcePermissions();
        Map<String, List<ResourcePermissionDto>> map = new HashMap<>(64);
        for (ResourcePermission resourcePermission : resourcePermissions) {
            String resourceName = resourcePermission.getResource();
            List<ResourcePermissionDto> resourcePermissionDtos = new ArrayList<>();
            for (ResourcePermission resourcePermission0 : resourcePermissions) {
                if (resourceName.equals(resourcePermission0.getResource())) {
                    ResourcePermissionDto resourcePermissionDto = new ResourcePermissionDto();
                    resourcePermissionDto.setResource(resourcePermission0.getResource());
                    resourcePermissionDto.setPermission(resourcePermission0.getPermission());
                    resourcePermissionDtos.add(resourcePermissionDto);
                }
            }
            map.put(resourceName, resourcePermissionDtos);
        }

        List<Resource> resources = this.resourceRepository.findAllEnabled();
        return resources.stream().map(resource -> {
            ResourceDetailDto resourceDetailDto = resourceDtoMapper.toResourceDetailDto(resource);
            List<ResourcePermissionDto> resourcePermissionDtos = map.get(resource.getName());
            resourceDetailDto.setResourcePermissions(resourcePermissionDtos);
            return resourceDetailDto;
        }).collect(Collectors.toList());
    }

    @Override
    public ResourceDetailDto detail(String resourceName) {
        Optional<Resource> resourceOptional = this.resourceRepository.findById(resourceName);
        if (!resourceOptional.isPresent()) {
            throw Exceptions.throwBusinessException(UserErrorCode.RESOURCE_DATA_NOT_EXISTS);
        }
        Resource resource = resourceOptional.get();
        ResourceDetailDto resourceDetailDto = resourceDtoMapper.toResourceDetailDto(resource);
        List<ResourcePermission> resourcePermissions = this.resourceRepository.getResourcePermissionsByResourceName(resourceName);
        if (null != resourcePermissions && !resourcePermissions.isEmpty()) {
            List<ResourcePermissionDto> resourcePermissionDtos = resourcePermissions.stream().map(resourceDtoMapper::toDto).collect(Collectors.toList());
            resourceDetailDto.setResourcePermissions(resourcePermissionDtos);
        } else {
            resourceDetailDto.setResourcePermissions(Lists.newArrayList());
        }
        return resourceDetailDto;
    }
}
