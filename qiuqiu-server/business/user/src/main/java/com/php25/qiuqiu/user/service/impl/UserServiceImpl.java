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
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.repository.model.User;
import com.php25.qiuqiu.user.service.UserService;
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
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author penghuiping
 * @date 2021/2/3 13:31
 */
@Log4j2
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RedisManager redisManager;

    private IdGenerator idGenerator;

    @Override
    public String login(String username, String password) {
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
                .setIssuer("wx-company-modules-user")
                .setIssuedAt(new Date())
                .setSubject("wx-company-modules-user")
                .setId(jti)
                .compact();

        //构造redisSession
        User user = userOptional.get();
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setUsername(user.getUsername());
        userSessionDto.setJti(jti);
        //todo 测试，写死角色
        userSessionDto.setRoles(Lists.newArrayList("admin"));
        redisManager.string().set(username, userSessionDto);
        return accessToken;
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

            if (!redisManager.exists(username.toString())) {
                return false;
            }

            String jti = claims.getId();
            UserSessionDto userSessionDto = redisManager.string().get(username.toString(),UserSessionDto.class);
            if(StringUtil.isBlank(jti) || !jti.equals(userSessionDto.getJti())) {
                throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
            }

            //并刷新过期时间
            redisManager.expire(username.toString(), 30L, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            log.error("jwt解析出错", e);
            throw Exceptions.throwBusinessException(UserErrorCode.JWT_ILLEGAL);
        }

    }

    @Override
    public Boolean logout(String username) {
        //清楚session会话信息
        redisManager.remove(username);
        return true;
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
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }


}
