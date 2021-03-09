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
import com.php25.qiuqiu.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        permission0.setEnable(permission.getEnable() == 1);
        permissionRepository.save(permission0);
        return true;
    }

    @Override
    public Boolean delete(List<Long> permissionIds) {
        //删除前判断是否有关联关系
        List<Long> roleIds = permissionRepository.getRoleIdsByPermissionIds(permissionIds);
        if (null != roleIds && !roleIds.isEmpty()) {
            throw Exceptions.throwBusinessException(UserErrorCode.PERMISSION_HAS_BEEN_REFERENCED_BY_ROLE);
        }
        List<Permission> permissions = permissionIds.stream().map(permissionId -> {
            Permission permission = new Permission();
            permission.setId(permissionId);
            return permission;
        }).collect(Collectors.toList());
        permissionRepository.deleteAll(permissions);
        return true;
    }

    @Override
    public DataGridPageDto<PermissionDto> page(String permissionName, Integer pageNum, Integer pageSize) {
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if (!StringUtil.isBlank(permissionName)) {
            builder.append(SearchParam.of("name", Operator.EQ, permissionName));
        }
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
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

    @Override
    public List<PermissionDto> getAll() {
        List<Permission> permissions = permissionRepository.findAllEnabled();
        if(null != permissions && !permissions.isEmpty()) {
            return permissions.stream().map(permission -> {
                PermissionDto permissionDto = new PermissionDto();
                BeanUtils.copyProperties(permission,permissionDto);
                return permissionDto;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
