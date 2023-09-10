package com.php25.qiuqiu.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleCreateDto;
import com.php25.qiuqiu.user.dto.role.RoleDetailDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.role.RolePageDto;
import com.php25.qiuqiu.user.dto.role.RoleUpdateDto;
import com.php25.qiuqiu.user.entity.ResourcePermission;
import com.php25.qiuqiu.user.entity.Role;
import com.php25.qiuqiu.user.entity.RoleResourcePermission;
import com.php25.qiuqiu.user.copyer.ResourceDtoCopyer;
import com.php25.qiuqiu.user.copyer.RoleDtoCopyer;
import com.php25.qiuqiu.user.repository.ResourceRepository;
import com.php25.qiuqiu.user.repository.RoleRepository;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/6 11:38
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService, InitializingBean {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ResourceRepository resourceRepository;

    private Map<String, Set<ResourcePermissionDto>> rolePermissionMap;

    private final RoleDtoCopyer roleDtoMapper;

    private final ResourceDtoCopyer resourceDtoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.loadPermissionRelation();
    }

    @Override
    @Transactional
    public Boolean create(RoleCreateDto role) {
        Role role0 = roleDtoMapper.toEntity(role);
        role0.setEnable(true);
        roleRepository.save(role0, true);

        //role与permission关系
        List<ResourcePermissionDto> permissions = role.getResourcePermissions();
        if (null != permissions && !permissions.isEmpty()) {
            List<RoleResourcePermission> permissionRefs = permissions.stream().map(resourcePermission -> {
                RoleResourcePermission roleResourcePermission = new RoleResourcePermission();
                roleResourcePermission.setPermission(resourcePermission.getPermission());
                roleResourcePermission.setResource(resourcePermission.getResource());
                roleResourcePermission.setRoleId(role0.getId());
                return roleResourcePermission;
            }).collect(Collectors.toList());
            roleRepository.createPermissionRefs(permissionRefs);
        }
        this.clearPermissionRelation();
        return true;
    }

    @Override
    @Transactional
    public Boolean update(RoleUpdateDto role) {
        Role role0 = roleDtoMapper.toEntity(role);
        roleRepository.save(role0, false);

        //role与permission关系
        List<ResourcePermissionDto> permissions = role.getResourcePermissions();
        if (null != permissions && !permissions.isEmpty()) {
            //先删除
            roleRepository.deletePermissionRefsByRoleId(role0.getId());
            List<RoleResourcePermission> permissionRefs = permissions.stream().map(resourcePermission -> {
                RoleResourcePermission roleResourcePermission = new RoleResourcePermission();
                roleResourcePermission.setPermission(resourcePermission.getPermission());
                roleResourcePermission.setResource(resourcePermission.getResource());
                roleResourcePermission.setRoleId(role0.getId());
                return roleResourcePermission;
            }).collect(Collectors.toList());
            roleRepository.createPermissionRefs(permissionRefs);
        }
        this.clearPermissionRelation();
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(List<Long> roleIds) {
        List<Long> userIds = userRepository.findUserIdsByRoleIds(roleIds);
        if (null != userIds && !userIds.isEmpty()) {
            throw Exceptions.throwBusinessException(UserErrorCode.ROLE_HAS_BEEN_REFERENCED_BY_USER);
        }
        List<Role> roles = roleIds.stream().map(roleId -> {
            Role role = new Role();
            role.setId(roleId);
            return role;
        }).collect(Collectors.toList());
        roleRepository.deleteAll(roles);
        roleRepository.deletePermissionRefsByRoleIds(roleIds);
        return true;
    }

    @Override
    public PageDto<RolePageDto> page(String roleName, Integer pageNum, Integer pageSize) {
        IPage<Role> page = roleRepository.page(roleName, pageNum, pageSize);
        PageDto<RolePageDto> res = new PageDto<>();
        List<RolePageDto> roleDtos = page.getRecords().stream().map(roleDtoMapper::toDto)
                .collect(Collectors.toList());
        res.setTotal(page.getTotal());
        res.setData(roleDtos);
        return res;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleRepository.findAllEnabled();
        return roles.stream().map(roleDtoMapper::toDto0).collect(Collectors.toList());
    }

    private void loadPermissionRelation() {
        this.rolePermissionMap = new HashMap<>(256);
        List<Role> roles = roleRepository.findAllEnabled();
        for (Role role : roles) {
            List<RoleResourcePermission> roleResourcePermissions = roleRepository.getPermissionsByRoleId(role.getId());
            List<ResourcePermission> resourcePermissions = resourceRepository.getAllResourcePermissions();
            Set<ResourcePermissionDto> set = new HashSet<>();
            for (ResourcePermission resourcePermission : resourcePermissions) {
                for (RoleResourcePermission permission : roleResourcePermissions) {
                    if (resourcePermission.getPermission().equals(permission.getPermission())
                            && resourcePermission.getResource().equals(permission.getResource())
                    ) {
                        ResourcePermissionDto resourcePermissionDto = resourceDtoMapper.toDto(resourcePermission);
                        set.add(resourcePermissionDto);
                    }
                }
            }
            this.rolePermissionMap.put(role.getName(), set);
        }
    }

    private void clearPermissionRelation() {
        synchronized (this) {
            this.rolePermissionMap = null;
        }
    }

    @Override
    public Set<ResourcePermissionDto> getPermissionsByRoleName(String roleName) {
        synchronized (this) {
            if (null == this.rolePermissionMap || this.rolePermissionMap.isEmpty()) {
                this.loadPermissionRelation();
            }
        }
        return this.rolePermissionMap.get(roleName);
    }

    @Override
    public RoleDetailDto detail(Long roleId) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (!roleOptional.isPresent()) {
            throw Exceptions.throwBusinessException(UserErrorCode.ROLE_DATA_NOT_EXISTS);
        }
        Role role = roleOptional.get();
        RoleDetailDto roleDetailDto = roleDtoMapper.toRoleDetailDto(role);
        roleDetailDto.setEnable(role.getEnable());
        Set<ResourcePermissionDto> resourcePermissionDtos = getPermissionsByRoleName(role.getName());
        roleDetailDto.setResourcePermissions(new ArrayList<>(resourcePermissionDtos));
        return roleDetailDto;
    }
}
