package com.php25.qiuqiu.user.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.user.entity.User;
import com.php25.qiuqiu.user.entity.UserRole;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/2/3 13:32
 */
public interface UserRepository {

    /**
     * 根据用户名从数据库中查询用户信息
     *
     * @return 数据库中用户信息
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据用户名与密码从数据库中查询用户信息
     *
     * @return 数据库中用户信息
     */
    Optional<User> findByUsernameAndPassword(String username, String password);


    /**
     * 通过用户id获取角色id列表
     *
     * @param userId 用户自增id
     * @return roleId列表
     */
    List<Long> findRoleIdsByUserId(Long userId);

    /**
     * 创建用户与角色关系
     *
     * @param roleRefs 用户与角色关系
     * @return true:成功
     */
    boolean createRoleRefs(List<UserRole> roleRefs);

    /**
     * 根据userId删除对应角色关系
     *
     * @param userId 用户id
     * @return true:成功
     */
    boolean deleteRoleRefsByUserId(Long userId);

    /**
     * 根据roleId列表获取userid列表
     *
     * @param roleIds 角色列表id
     * @return 用户id列表
     */
    List<Long> findUserIdsByRoleIds(List<Long> roleIds);

    /**
     * 获取在某个组中的用户数
     *
     * @param groupId 组id
     * @return 在组中的用户数
     */
    Long countByGroupId(Long groupId);

    /**
     * 保存
     *
     * @param user     用户
     * @param isInsert true:新增 false:更新
     * @return true:成功
     */
    boolean save(User user, boolean isInsert);


    /**
     * 根据id主键获取用户
     *
     * @param id 主键
     * @return 用户
     */
    Optional<User> findById(Long id);

    /**
     * 物理删除用户
     *
     * @param id 用户id
     * @return true:删除成功
     */
    boolean deleteById(Long id);

    /**
     * 用户分页查询
     *
     * @param username 用户名
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 用户分页数据
     */
    IPage<User> page(String username, Integer pageNum, Integer pageSize);

}
