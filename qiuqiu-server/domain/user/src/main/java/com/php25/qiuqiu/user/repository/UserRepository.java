package com.php25.qiuqiu.user.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.user.repository.model.User;

import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/2/3 13:32
 */
public interface UserRepository extends BaseDbRepository<User,Long> {

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
}
