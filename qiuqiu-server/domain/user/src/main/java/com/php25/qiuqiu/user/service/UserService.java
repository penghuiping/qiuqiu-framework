package com.php25.qiuqiu.user.service;

import com.php25.qiuqiu.user.service.dto.TokenDto;
import com.php25.qiuqiu.user.service.dto.UserDto;

/**
 * 系统用户
 *
 * @author penghuiping
 * @date 2021/2/3 13:23
 */
public interface UserService {

    /**
     * 用户登入接口
     *
     * @param username 用户名
     * @param password 密码
     * @return jwt令牌, 后面访问本系统接口，都需要带上此jwt令牌
     */
    TokenDto login(String username, String password);

    /**
     * 验证jwt令牌是否有效
     *
     * @param jwt 令牌
     * @return true:有效,false:无效
     */
    Boolean isTokenValid(String jwt);

    /**
     * 根据jwt获取用户名
     *
     * @param jwt 令牌
     * @return 用户名
     */
    String getUsernameFromJwt(String jwt);

    /**
     * 登出接口
     *
     * @param username 用户名
     * @return true:登出成功,false:登出失败
     */
    Boolean logout(String username);

    /**
     * 获取持有jwt令牌的用户信息接口
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserDto getUserInfo(String username);

    /**
     * 判断某个用户是否具有访问某个接口地址的权限
     *
     * @param username 用户名
     * @param uri      接口地址
     * @return true: 有权限
     */
    Boolean hasPermission(String username, String uri);
}
