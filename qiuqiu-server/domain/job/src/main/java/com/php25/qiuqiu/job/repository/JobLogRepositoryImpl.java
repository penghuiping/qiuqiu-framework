package com.php25.qiuqiu.job.repository;

import com.php25.common.db.DbType;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.job.entity.JobLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2021/3/15 14:05
 */
@Repository
public class JobLogRepositoryImpl extends BaseDbRepositoryImpl<JobLog, Long> implements JobLogRepository {

    public JobLogRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }
}
