package com.php25.qiuqiu.user.service;

import com.php25.common.core.dto.PageDto;
import com.php25.qiuqiu.user.dto.user.TokenDto;
import com.php25.qiuqiu.user.dto.user.UserCreateDto;
import com.php25.qiuqiu.user.dto.user.UserDto;
import com.php25.qiuqiu.user.dto.user.UserPageDto;
import com.php25.qiuqiu.user.dto.user.UserUpdateDto;

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
     * @return 访问令牌, 后面访问本系统接口，都需要带上此访问令牌
     */
    TokenDto login(String username, String password);

    /**
     * 验证访问令牌是否有效
     *
     * @param jwt 访问令牌
     * @return true:有效,false:无效
     */
    Boolean isTokenValid(String jwt);

    /**
     * 通过刷新令牌重新获取访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return jwt令牌, 后面访问本系统接口，都需要带上此jwt令牌
     */
    TokenDto refreshToken(String refreshToken);

    /**
     * 根据访问令牌获取用户名
     *
     * @param jwt 访问令牌
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
     * 用户详情
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserDto detail(Long userId);

    /**
     * 判断某个用户是否具有访问某个接口地址的权限
     *
     * @param jwt 访问令牌
     * @param uri 接口地址
     * @return true: 有权限
     */
    Boolean hasPermission(String jwt, String uri);

    /**
     * 用户列表分页
     *
     * @param username 用户名
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 分页数据结果
     */
    PageDto<UserPageDto> page(String username, Integer pageNum, Integer pageSize);

    /**
     * 创建用户
     *
     * @param userCreateDto 可以创建的用户信息
     * @return true:成功
     */
    Boolean create(UserCreateDto userCreateDto);

    /**
     * 修改用户
     *
     * @param userUpdateDto 可以更新的用户信息
     * @return true:成功
     */
    Boolean update(UserUpdateDto userUpdateDto);

    /**
     * 物理删除用户信息
     *
     * @param userId 用户id
     * @return true:删除成功
     */
    Boolean delete(Long userId);
}
