package com.php25.qiuqiu.user.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.qiuqiu.user.service.dto.TokenDto;
import com.php25.qiuqiu.user.service.dto.UserCreateDto;
import com.php25.qiuqiu.user.service.dto.UserDto;
import com.php25.qiuqiu.user.service.dto.UserPageDto;
import com.php25.qiuqiu.user.service.dto.UserUpdateDto;

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

    /**
     * 用户列表分页
     *
     * @param username 用户名
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 分页数据结果
     */
    DataGridPageDto<UserPageDto> page(String username, Integer pageNum, Integer pageSize);

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
