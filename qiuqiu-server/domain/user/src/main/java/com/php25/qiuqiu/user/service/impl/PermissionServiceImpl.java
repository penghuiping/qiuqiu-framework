package com.php25.qiuqiu.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.entity.Permission;
import com.php25.qiuqiu.user.mapper.PermissionDtoMapper;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import com.php25.qiuqiu.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/6 12:42
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    private final PermissionDtoMapper permissionDtoMapper;

    @Override
    public Boolean create(PermissionCreateDto permission) {
        Permission permission0 = permissionDtoMapper.toEntity(permission);
        permission0.setEnable(true);
        permissionRepository.save(permission0, true);
        return true;
    }

    @Override
    public Boolean update(PermissionDto permission) {
        Permission permission0 = permissionDtoMapper.toEntity(permission);
        permission0.setEnable(permission.getEnable());
        permissionRepository.save(permission0, false);
        return true;
    }

    @Override
    public Boolean delete(String permissionName) {
        //删除前判断是否有关联关系
        Boolean res = permissionRepository.hasReferencedByResource(permissionName);
        if (res) {
            throw Exceptions.throwBusinessException(UserErrorCode.PERMISSION_HAS_BEEN_REFERENCED_BY_RESOURCE);
        }
        Permission permission = new Permission();
        permission.setName(permissionName);
        permissionRepository.delete(permission);
        return true;
    }

    @Override
    public PageDto<PermissionDto> page(String permissionName, Integer pageNum, Integer pageSize) {
        IPage<Permission> page = permissionRepository.page(permissionName, pageNum, pageSize);
        PageDto<PermissionDto> dataGridPageDto = new PageDto<>();
        List<PermissionDto> permissionDtos = page.getRecords().stream()
                .map(permissionDtoMapper::toDto).collect(Collectors.toList());
        dataGridPageDto.setData(permissionDtos);
        return dataGridPageDto;
    }

    @Override
    public List<PermissionDto> getAll() {
        List<Permission> permissions = permissionRepository.findAllEnabled();
        return permissions.stream().map(permissionDtoMapper::toDto).collect(Collectors.toList());
    }
}
