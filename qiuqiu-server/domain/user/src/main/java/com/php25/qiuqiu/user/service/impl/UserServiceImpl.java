package com.php25.qiuqiu.user.service.impl;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.DigestUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.crypto.constant.SignAlgorithm;
import com.php25.common.core.util.crypto.key.SecretKeyUtil;
import com.php25.common.core.util.jwt.JwtUtil;
import com.php25.common.core.util.jwt.UserRoleInfo;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.qiuqiu.user.constant.DataAccessLevel;
import com.php25.qiuqiu.user.constant.UserConstants;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.dto.resource.ResourcePermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.user.TokenDto;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import com.php25.qiuqiu.user.entity.Group;
import com.php25.qiuqiu.user.entity.Role;
import com.php25.qiuqiu.user.entity.RoleResourcePermission;
import com.php25.qiuqiu.user.entity.User;
import com.php25.qiuqiu.user.entity.UserRole;
import com.php25.qiuqiu.user.dto.mapper.PermissionDtoMapper;
import com.php25.qiuqiu.user.dto.mapper.RoleDtoMapper;
import com.php25.qiuqiu.user.dto.mapper.UserDtoMapper;
import com.php25.qiuqiu.user.repository.GroupRepository;
import com.php25.qiuqiu.user.repository.RoleRepository;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.service.RoleService;
import com.php25.qiuqiu.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/2/3 13:31
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final GroupRepository groupRepository;

    private final IdGenerator idGenerator;

    private final AntPathMatcher antPathMatcher;

    private final RoleService roleService;

    private final UserDtoMapper userDtoMapper;

    private final RoleDtoMapper roleDtoMapper;

    @Override
    public TokenDto login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        if (!userOptional.isPresent()) {
            throw Exceptions.throwBusinessException(UserErrorCode.USER_NOT_FOUND);
        }
        return generateToken(username, "");
    }


    private TokenDto generateToken(String username, String refreshToken) {
        String jti = idGenerator.getUUID();
        RSAPrivateKey privateKey = (RSAPrivateKey) SecretKeyUtil.generatePrivateKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PRIVATE_KEY));
        UserDto userDto = this.getUserInfo(username);

        //生成accessToken
        List<String> roles = userDto.getRoles().stream().map(RoleDto::getName).collect(Collectors.toList());
        String accessToken = JwtUtil.generateToken(jti, username, roles, 1800L, "QiuQiu", privateKey);

        if (StringUtil.isBlank(refreshToken)) {
            refreshToken = JwtUtil.generateRefreshToken(idGenerator.getUUID(), username, 3600L * 24 * 7, "QiuQiu", privateKey);
        }
        return new TokenDto(accessToken, refreshToken, 1800L);
    }

    @Override
    public Boolean isTokenValid(String jwt) {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PUBLIC_KEY));
            if (!JwtUtil.isValidSign(jwt, publicKey)) {
                return false;
            }
            UserRoleInfo userRoleInfo = JwtUtil.parse(jwt, publicKey);
            if (!userRoleInfo.isValid()) {
                throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
            }
            return true;
        } catch (ExpiredJwtException e) {
            //jwt过期
            throw Exceptions.throwBusinessException(UserErrorCode.JWT_EXPIRED);
        } catch (Exception e) {
            log.error("jwt解析出错", e);
            throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
        }
    }

    @Override
    public TokenDto refreshToken(String refreshToken) {
        RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PUBLIC_KEY));
        String username = null;
        try {
            UserRoleInfo userRoleInfo = JwtUtil.parse(refreshToken, publicKey);
            username = userRoleInfo.getUsername();
        } catch (Exception e) {
            log.error("refreshToken解析出错", e);
            throw Exceptions.throwBusinessException(UserErrorCode.REFRESH_TOKEN_ILLEGAL);
        }
        return generateToken(username, refreshToken);
    }

    @Override
    public Boolean logout(String username) {
        return true;
    }

    @Override
    public String getUsernameFromJwt(String jwt) {
        RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PUBLIC_KEY));
        UserRoleInfo userRoleInfo = JwtUtil.parse(jwt, publicKey);
        return userRoleInfo.getUsername();
    }

    @Override
    public UserDto getUserInfo(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw Exceptions.throwImpossibleException();
        }
        User user = userOptional.get();

        //角色
        List<Long> roleIds = userRepository.findRoleIdsByUserId(user.getId());
        List<Role> roles = (List<Role>) roleRepository.findAllById(roleIds);

        UserDto userDto = userDtoMapper.toDto(user);
        Set<RoleDto> roleDtoSet = roles.stream().map(roleDtoMapper::toDto0).collect(Collectors.toSet());
        userDto.setRoles(roleDtoSet);
        userDto.setDataAccessLevel(DataAccessLevel.valueOf(user.getDataAccessLevel()));
        userDto.setEnable(user.getEnable());

        //权限
        List<RoleResourcePermission> permissions = roleRepository.getPermissionsByRoleIds(roleIds);
        Set<ResourcePermissionDto> permissionDtos = permissions.stream().map(permission -> {
            ResourcePermissionDto permissionDto = new ResourcePermissionDto();
            BeanUtils.copyProperties(permission, permissionDto);
            return permissionDto;
        }).collect(Collectors.toSet());
        userDto.setPermissions(permissionDtos);

        //组
        Optional<Group> groupOptional = groupRepository.findById(user.getGroupId());
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group, groupDto);
            userDto.setGroup(groupDto);
        }
        return userDto;
    }

    @Override
    public UserDto detail(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw Exceptions.throwBusinessException(UserErrorCode.USER_DATA_NOT_EXISTS);
        }
        User user = userOptional.get();

        //角色
        List<Long> roleIds = userRepository.findRoleIdsByUserId(user.getId());
        List<Role> roles = (List<Role>) roleRepository.findAllById(roleIds);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        Set<RoleDto> roleDtoSet = roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toSet());
        userDto.setRoles(roleDtoSet);
        userDto.setEnable(user.getEnable());

        //权限
        List<RoleResourcePermission> permissions = roleRepository.getPermissionsByRoleIds(roleIds);
        Set<ResourcePermissionDto> permissionDtos = permissions.stream().map(permission -> {
            ResourcePermissionDto permissionDto = new ResourcePermissionDto();
            BeanUtils.copyProperties(permission, permissionDto);
            return permissionDto;
        }).collect(Collectors.toSet());
        userDto.setPermissions(permissionDtos);

        //组
        Optional<Group> groupOptional = groupRepository.findById(user.getGroupId());
        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group, groupDto);
            userDto.setGroup(groupDto);
        }
        return userDto;
    }

    @Override
    public Boolean hasPermission(String jwt, String uri) {
        RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PUBLIC_KEY));
        UserRoleInfo userRoleInfo = JwtUtil.parse(jwt, publicKey);
        List<String> roleNames = userRoleInfo.getRoleNames();
        for (String roleName : roleNames) {
            Set<ResourcePermissionDto> permissions = roleService.getPermissionsByRoleName(roleName);
            for (ResourcePermissionDto permissionDto : permissions) {
                String uri0 = String.format("/%s/%s", permissionDto.getResource(), permissionDto.getPermission());
                if (antPathMatcher.match("/**" + uri0, uri)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DataGridPageDto<UserPageDto> page(String username, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if (!StringUtil.isBlank(username)) {
            builder.append(SearchParam.of("username", Operator.EQ, username));
        }
        Page<User> userPage = userRepository.findAll(builder, pageRequest);
        DataGridPageDto<UserPageDto> res = new DataGridPageDto<>();
        List<UserPageDto> list = userPage.get().map(user -> {
            UserPageDto userPageDto = new UserPageDto();
            BeanUtils.copyProperties(user, userPageDto);
            userPageDto.setEnable(user.getEnable());
            return userPageDto;
        }).collect(Collectors.toList());
        res.setData(list);
        res.setRecordsTotal(userPage.getTotalElements());
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(UserCreateDto userCreateDto) {
        User user = new User();
        BeanUtils.copyProperties(userCreateDto, user);
        user.setCreateTime(LocalDateTime.now());
        user.setLastModifiedTime(LocalDateTime.now());
        user.setEnable(true);
        user.setIsNew(true);
        userRepository.save(user);

        List<UserRole> roleRefs = userCreateDto.getRoleIds().stream().map(roleId -> {
            UserRole roleRef = new UserRole();
            roleRef.setRoleId(roleId);
            roleRef.setUserId(user.getId());
            return roleRef;
        }).collect(Collectors.toList());

        userRepository.createRoleRefs(roleRefs);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(UserUpdateDto userUpdateDto) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDto, user);
        user.setLastModifiedTime(LocalDateTime.now());
        user.setIsNew(false);
        userRepository.save(user);
        if (null != userUpdateDto.getRoleIds() && !userUpdateDto.getRoleIds().isEmpty()) {
            List<UserRole> roleRefs = userUpdateDto.getRoleIds().stream().map(roleId -> {
                UserRole roleRef = new UserRole();
                roleRef.setRoleId(roleId);
                roleRef.setUserId(user.getId());
                return roleRef;
            }).collect(Collectors.toList());

            userRepository.deleteRoleRefsByUserId(user.getId());
            userRepository.createRoleRefs(roleRefs);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long userId) {
        userRepository.deleteById(userId);
        userRepository.deleteRoleRefsByUserId(userId);
        return true;
    }
}
