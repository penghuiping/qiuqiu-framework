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
import com.php25.common.redis.RedisManager;
import com.php25.qiuqiu.user.constant.UserConstants;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.group.GroupDto;
import com.php25.qiuqiu.user.dto.permission.PermissionDto;
import com.php25.qiuqiu.user.dto.role.RoleDto;
import com.php25.qiuqiu.user.dto.user.TokenDto;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserSessionDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;
import com.php25.qiuqiu.user.model.Group;
import com.php25.qiuqiu.user.model.Permission;
import com.php25.qiuqiu.user.model.Role;
import com.php25.qiuqiu.user.model.RoleRef;
import com.php25.qiuqiu.user.model.User;
import com.php25.qiuqiu.user.repository.GroupRepository;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import com.php25.qiuqiu.user.repository.RoleRepository;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.service.RoleService;
import com.php25.qiuqiu.user.service.UserService;
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

    private final PermissionRepository permissionRepository;

    private final RedisManager redisManager;

    private final IdGenerator idGenerator;

    private final AntPathMatcher antPathMatcher;

    private final RoleService roleService;

    @Override
    public TokenDto login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        if (!userOptional.isPresent()) {
            throw Exceptions.throwBusinessException(UserErrorCode.USER_NOT_FOUND);
        }
        String jti = idGenerator.getUUID();
        RSAPrivateKey privateKey = (RSAPrivateKey) SecretKeyUtil.generatePrivateKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PRIVATE_KEY));
        //存入redis
        redisManager.string().set(getRedisJwtKey(username), jti, 1800L);
        //构造redisSession
        UserSessionDto userSessionDto = this.createUserSession(username, 1800L);

        //生成accessToken
        List<String> roles = userSessionDto.getRoles().stream().map(RoleDto::getName).collect(Collectors.toList());
        String accessToken = JwtUtil.generateToken(jti, username, roles, 1800L, "QiuQiu", privateKey);
        return new TokenDto(accessToken, 1800L);
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

            String username = userRoleInfo.getUsername();
            if (!redisManager.exists(getRedisJwtKey(username))) {
                return false;
            }
            String jti = userRoleInfo.getJti();

            String jti0 = redisManager.string().get(getRedisJwtKey(username), String.class);
            if (StringUtil.isBlank(jti) || !jti.equals(jti0)) {
                throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
            }
            return true;
        } catch (Exception e) {
            log.error("jwt解析出错", e);
            throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
        }
    }

    @Override
    public Boolean logout(String username) {
        //清楚session会话信息
        redisManager.remove(getRedisJwtKey(username));
        redisManager.remove(getRedisSessionKey(username));
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
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        Set<RoleDto> roleDtoSet = roles.stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toSet());
        userDto.setRoles(roleDtoSet);
        userDto.setEnable(user.getEnable() ? 1 : 0);

        //权限
        List<Long> permissionIds = roleRepository.getPermissionIdsByRoleIds(roleIds);
        List<Permission> permissions = (List<Permission>) permissionRepository.findAllById(permissionIds);
        Set<PermissionDto> permissionDtos = permissions.stream().map(permission -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(permission, permissionDto);
            return permissionDto;
        }).collect(Collectors.toSet());
        userDto.setPermissions(permissionDtos);

        //组
        Optional<Group> groupOptional = groupRepository.findByIdEnable(user.getGroupId());
        if (!groupOptional.isPresent()) {
            throw Exceptions.throwImpossibleException();
        }
        Group group = groupOptional.get();
        GroupDto groupDto = new GroupDto();
        BeanUtils.copyProperties(group, groupDto);
        userDto.setGroup(groupDto);
        return userDto;
    }

    @Override
    public UserDto detail(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw Exceptions.throwImpossibleException();
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
        userDto.setEnable(user.getEnable() ? 1 : 0);

        //权限
        List<Long> permissionIds = roleRepository.getPermissionIdsByRoleIds(roleIds);
        List<Permission> permissions = (List<Permission>) permissionRepository.findAllById(permissionIds);
        Set<PermissionDto> permissionDtos = permissions.stream().map(permission -> {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(permission, permissionDto);
            return permissionDto;
        }).collect(Collectors.toSet());
        userDto.setPermissions(permissionDtos);

        //组
        Optional<Group> groupOptional = groupRepository.findByIdEnable(user.getGroupId());
        if (!groupOptional.isPresent()) {
            throw Exceptions.throwImpossibleException();
        }
        Group group = groupOptional.get();
        GroupDto groupDto = new GroupDto();
        BeanUtils.copyProperties(group, groupDto);
        userDto.setGroup(groupDto);
        return userDto;
    }

    @Override
    public Boolean hasPermission(String username, String uri) {
        //先通过session判断
        UserSessionDto userSessionDto = getUserSession(username);
        if (null == userSessionDto) {
            return false;
        }

        Set<RoleDto> roles = userSessionDto.getRoles();
        for (RoleDto roleDto : roles) {
            String roleName = roleDto.getName();
            Set<PermissionDto> permissions = roleService.getPermissionsByRoleName(roleName);
            for (PermissionDto permissionDto : permissions) {
                if (antPathMatcher.match("/**" + permissionDto.getUri(), uri)) {
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
            userPageDto.setEnable(user.getEnable() ? 1 : 0);
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

        List<RoleRef> roleRefs = userCreateDto.getRoleIds().stream().map(roleId -> {
            RoleRef roleRef = new RoleRef();
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
            List<RoleRef> roleRefs = userUpdateDto.getRoleIds().stream().map(roleId -> {
                RoleRef roleRef = new RoleRef();
                roleRef.setRoleId(roleId);
                roleRef.setUserId(user.getId());
                return roleRef;
            }).collect(Collectors.toList());

            userRepository.deleteRoleRefsByUserId(user.getId());
            userRepository.createRoleRefs(roleRefs);
        }
        clearUserSession(user.getUsername());
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long userId) {
        userRepository.deleteById(userId);
        userRepository.deleteRoleRefsByUserId(userId);
        return true;
    }

    @Override
    public UserSessionDto createUserSession(String username, Long expireTime) {
        String jti = redisManager.string().get(getRedisJwtKey(username), String.class);
        //构造redisSession
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setUsername(username);
        userSessionDto.setJti(jti);

        UserDto userDto = this.getUserInfo(username);
        //角色
        userSessionDto.setRoles(userDto.getRoles());
        redisManager.string().set(getRedisSessionKey(username), userSessionDto, expireTime);
        return userSessionDto;
    }

    @Override
    public UserSessionDto getUserSession(String username) {
        if (!redisManager.exists(getRedisSessionKey(username))) {
            this.createUserSession(username, 1800L);
        }
        return redisManager.string().get(getRedisSessionKey(username), UserSessionDto.class);
    }

    @Override
    public void clearUserSession(String username) {
        redisManager.remove(getRedisSessionKey(username));
    }

    private String getRedisJwtKey(String username) {
        return UserConstants.JWT_REDIS_PREFIX + username;
    }

    private String getRedisSessionKey(String username) {
        return UserConstants.SESSION_PREFIX + username;
    }


}
