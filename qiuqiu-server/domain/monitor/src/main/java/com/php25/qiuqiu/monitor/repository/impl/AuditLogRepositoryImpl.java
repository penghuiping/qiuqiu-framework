package com.php25.qiuqiu.monitor.repository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.monitor.entity.AuditLog;
import com.php25.qiuqiu.monitor.repository.AuditLogRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2021/3/11 15:09
 */
@Repository
public class AuditLogRepositoryImpl extends BaseDbRepositoryImpl<AuditLog, Long> implements AuditLogRepository {

    public AuditLogRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }
}
