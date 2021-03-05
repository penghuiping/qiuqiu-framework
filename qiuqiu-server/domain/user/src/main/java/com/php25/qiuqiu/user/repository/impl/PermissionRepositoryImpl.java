package com.php25.qiuqiu.user.repository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.user.repository.PermissionRepository;
import com.php25.qiuqiu.user.repository.model.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2021/3/5 17:41
 */
@Component
public class PermissionRepositoryImpl extends BaseDbRepositoryImpl<Permission, Long> implements PermissionRepository {

    public PermissionRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }
}
