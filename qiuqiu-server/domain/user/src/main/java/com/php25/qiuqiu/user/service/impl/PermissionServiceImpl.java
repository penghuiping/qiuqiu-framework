package com.php25.qiuqiu.user.service.impl;

import com.google.common.collect.Lists;
import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.StringUtil;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.permission.PermissionCreateDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.model.Permission;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import com.php25.qiuqiu.user.repository.ResourceRepository;
import com.php25.qiuqiu.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private final ResourceRepository resourceRepository;

    @Override
    public Boolean create(PermissionCreateDto permission) {
        Permission permission0 = new Permission();
        BeanUtils.copyProperties(permission, permission0);
        permission0.setEnable(true);
        permission0.setIsNew(true);
        permissionRepository.save(permission0);
        return true;
    }

    @Override
    public Boolean update(PermissionDto permission) {
        Permission permission0 = new Permission();
        BeanUtils.copyProperties(permission, permission0);
        permission0.setIsNew(false);
        permission0.setEnable(permission.getEnable());
        permissionRepository.save(permission0);
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
    public DataGridPageDto<PermissionDto> page(String permissionName, Integer pageNum, Integer pageSize) {
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if (!StringUtil.isBlank(permissionName)) {
            builder.append(SearchParam.of("name", Operator.EQ, permissionName));
        }
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Permission> page = permissionRepository.findAll(builder, pageRequest);
        DataGridPageDto<PermissionDto> dataGridPageDto = new DataGridPageDto<>();
        List<PermissionDto> permissionDtos = page.get().map(permission -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(permission, permissionDto);
            return permissionDto;
        }).collect(Collectors.toList());
        dataGridPageDto.setData(permissionDtos);
        return dataGridPageDto;
    }
}
