package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.RandomUtil;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.common.redis.RedisManager;
import com.php25.qiuqiu.admin.constant.AdminErrorCode;
import com.php25.qiuqiu.admin.vo.in.LoginVo;
import com.php25.qiuqiu.admin.vo.out.TokenVo;
import com.php25.qiuqiu.media.service.ImageService;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.user.constant.UserConstants;
import com.php25.qiuqiu.user.constant.UserErrorCode;
import com.php25.qiuqiu.user.dto.user.TokenDto;
import com.php25.qiuqiu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2022/3/31 22:02
 */
@Slf4j
@RestController
@RequestMapping("/other")
@RequiredArgsConstructor
public class OtherController extends JSONController {

    private final RedisManager redisManager;

    private final UserService userService;

    private final ImageService imageService;

    /**
     * 登入接口
     *
     * @ignoreParams response
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/login",headers = {"version=v1"})
    public JSONResponse<TokenVo> login(HttpServletResponse response,
                                       @Valid @RequestBody LoginVo loginVo) {
        String rightCode = redisManager.string().get(loginVo.getImgCodeId(),String.class);
        if(null == rightCode || !rightCode.equals(loginVo.getCode())) {
            throw Exceptions.throwBusinessException(AdminErrorCode.IMAGE_VALIDATION_CODE_ERROR);
        }
        TokenDto tokenDto = userService.login(loginVo.getUsername(), loginVo.getPassword());
        TokenVo tokenVo = new TokenVo();
        tokenVo.setToken(tokenDto.getAccessToken());
        tokenVo.setExpireTime(tokenDto.getExpireTime());
        Cookie cookie = new Cookie(UserConstants.REFRESH_TOKEN, tokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return succeed(tokenVo);
    }

    /**
     * 登出接口
     */
    @AuditLog
    @PostMapping(value = "/logout",headers = {"version=v1"})
    public JSONResponse<Boolean> logout(@RequestAttribute @NotBlank String username) {
        return succeed(userService.logout(username));
    }

    /**
     * 获取登入验证码
     */
    @GetMapping(value = "/img_code")
    public void getImgCode(@NotBlank @Length(max = 32) @RequestParam String imgCodeId, HttpServletResponse response) {
        String code = RandomUtil.getRandomNumbersAndLetters(6);
        //验证码过期时间5分钟
        redisManager.string().set(imgCodeId,code,300L);
        try (
                ReadableByteChannel readableByteChannel = imageService.getCode(code);
                WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(512);
            while (true) {
                buffer.clear();
                int size = readableByteChannel.read(buffer);
                if (size <= 0) {
                    break;
                }
                buffer.flip();
                writableByteChannel.write(buffer);
            }
        } catch (Exception e) {
            throw Exceptions.throwIllegalStateException("获取验证码失败", e);
        }
    }

    /**
     * 刷新token接口
     *
     * @ignoreParams request
     */
    @AuditLog
    @PostMapping(value = "/refresh",headers = {"version=v1"})
    public JSONResponse<TokenVo> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                    .filter(cookie -> UserConstants.REFRESH_TOKEN.equals(cookie.getName()))
                    .findFirst();
            if (cookieOptional.isPresent()) {
                Cookie cookie = cookieOptional.get();
                TokenDto tokenDto = userService.refreshToken(cookie.getValue());
                TokenVo tokenVo = new TokenVo();
                tokenVo.setToken(tokenDto.getAccessToken());
                tokenVo.setExpireTime(tokenDto.getExpireTime());
                return succeed(tokenVo);
            }
        }
        throw Exceptions.throwBusinessException(UserErrorCode.REFRESH_TOKEN_ILLEGAL);
    }
}
