package com.php25.qiuqiu.user.repository.impl;

import com.google.common.collect.Lists;
import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.repository.UserRepository;
import com.php25.qiuqiu.user.repository.model.RoleRef;
import com.php25.qiuqiu.user.repository.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/2/3 14:51
 */
@Component
public class UserRepositoryImpl extends BaseDbRepositoryImpl<User, Long> implements UserRepository {

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        SqlParams sqlParams = Queries.of(dbType).from(User.class).whereEq("username", username).single();
        User user = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
        if (null == user) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        SqlParams sqlParams = Queries.of(dbType).from(User.class)
                .whereEq("username", username)
                .andEq("password", password)
                .single();
        User user = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
        if (null == user) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public List<Long> findRoleIdsByUserId(Long userId) {
        SqlParams sqlParams = Queries.of(dbType).from(RoleRef.class)
                .whereEq("userId", userId)
                .select();
        List<RoleRef> roleRefs = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
        if (null != roleRefs && !roleRefs.isEmpty()) {
            return roleRefs.stream().map(RoleRef::getRoleId).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }
}
