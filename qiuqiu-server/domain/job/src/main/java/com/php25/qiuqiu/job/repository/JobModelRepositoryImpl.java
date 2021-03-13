package com.php25.qiuqiu.job.repository;

import com.php25.common.db.DbType;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.job.model.JobModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2020/8/24 13:56
 */
@Repository
public class JobModelRepositoryImpl extends BaseDbRepositoryImpl<JobModel, String> implements JobModelRepository {

    public JobModelRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }
}
