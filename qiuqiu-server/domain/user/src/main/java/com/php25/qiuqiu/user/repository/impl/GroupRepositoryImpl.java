package com.php25.qiuqiu.user.repository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.repository.GroupRepository;
import com.php25.qiuqiu.user.repository.model.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2021/3/5 17:12
 */
@Component
public class GroupRepositoryImpl extends BaseDbRepositoryImpl<Group, Long> implements GroupRepository {

    public GroupRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }
}
