package com.php25.qiuqiu.user.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.DigestUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.crypto.constant.SignAlgorithm;
import com.php25.common.core.util.crypto.key.SecretKeyUtil;
import com.php25.common.redis.RedisManager;
import com.php25.qiuqiu.user.constant.UserConstants;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.repository.GroupRepository;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import com.php25.qiuqiu.user.repository.RoleRepository;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.repository.model.Group;
import com.php25.qiuqiu.user.repository.model.Permission;
import com.php25.qiuqiu.user.repository.model.Role;
import com.php25.qiuqiu.user.repository.model.User;
import com.php25.qiuqiu.user.service.UserService;
import com.php25.qiuqiu.user.service.dto.GroupDto;
import com.php25.qiuqiu.user.service.dto.PermissionDto;
import com.php25.qiuqiu.user.service.dto.RoleDto;
import com.php25.qiuqiu.user.service.dto.TokenDto;
import com.php25.qiuqiu.user.service.dto.UserDto;
import com.php25.qiuqiu.user.service.dto.UserSessionDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
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
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final GroupRepository groupRepository;

    private final PermissionRepository permissionRepository;

    private final RedisManager redisManager;

    private final IdGenerator idGenerator;

    @Override
    public TokenDto login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        if (!userOptional.isPresent()) {
            throw Exceptions.throwBusinessException(UserErrorCode.USER_NOT_FOUND);
        }
        String jti = idGenerator.getUUID();

        RSAPrivateKey privateKey = (RSAPrivateKey) SecretKeyUtil.generatePrivateKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PRIVATE_KEY));

        String accessToken = Jwts.builder().signWith(privateKey)
                .setClaims(Maps.toMap(Lists.newArrayList("username"), s -> {
                    switch (s) {
                        case "username":
                            return username;
                        default:
                            return null;
                    }
                }))
                .setIssuer("QiuQiu-Admin")
                .setIssuedAt(new Date())
                .setSubject("QiuQiu-Admin")
                .setId(jti)
                .compact();

        //存入redis
        redisManager.string().set(getRedisJwtKey(username), jti, 1800L);

        //构造redisSession
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setUsername(username);
        userSessionDto.setJti(jti);

        UserDto userDto = this.getUserInfo(username);
        //角色
        userSessionDto.setRoles(userDto.getRoles());
        //权限
        userSessionDto.setPermissions(userDto.getPermissions());
        redisManager.string().set(getRedisSessionKey(username), userSessionDto, 1800L);
        return new TokenDto(accessToken, 1800L);
    }

    @Override
    public Boolean isTokenValid(String jwt) {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PUBLIC_KEY));
            boolean isSigned = Jwts.parser().setSigningKey(publicKey).isSigned(jwt);
            if (!isSigned) {
                return false;
            }
            Jws<Claims> jwtObject = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt);
            Claims claims = jwtObject.getBody();
            Object username = claims.getOrDefault("username", null);
            if (username == null || StringUtil.isBlank(username.toString())) {
                throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
            }

            if (!redisManager.exists(getRedisJwtKey(username.toString()))) {
                return false;
            }

            String jti = claims.getId();
            UserSessionDto userSessionDto = redisManager.string().get(getRedisSessionKey(username.toString()), UserSessionDto.class);
            if (StringUtil.isBlank(jti) || !jti.equals(userSessionDto.getJti())) {
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

    private String getRedisJwtKey(String username) {
        return UserConstants.JWT_REDIS_PREFIX + username;
    }

    private String getRedisSessionKey(String username) {
        return UserConstants.SESSION_PREFIX + username;
    }


    @Override
    public String getUsernameFromJwt(String jwt) {
        RSAPublicKey publicKey = (RSAPublicKey) SecretKeyUtil.generatePublicKey(SignAlgorithm.SHA256withRSA.getValue(), DigestUtil.decodeBase64(UserConstants.JWT_PUBLIC_KEY));
        Jws<Claims> jwtObject = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt);
        Claims claims = jwtObject.getBody();
        Object username = claims.getOrDefault("username", null);
        return username.toString();
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
    public Boolean hasPermission(String username, String uri) {
        //先通过session判断
        UserSessionDto userSessionDto = redisManager.string().get(getRedisSessionKey(username), UserSessionDto.class);
        if (null == userSessionDto) {
            return false;
        }
        Set<PermissionDto> permissions = userSessionDto.getPermissions();

        boolean res = false;
        for (PermissionDto permissionDto : permissions) {
            if (uri.endsWith(permissionDto.getUri())) {
                res = true;
                break;
            }
        }
        return res;
    }
}
