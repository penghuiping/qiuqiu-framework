package com.php25.qiuqiu.job.repository;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.qiuqiu.job.model.JobExecution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/15 20:37
 */
@Repository
public class JobExecutionRepositoryImpl extends BaseDbRepositoryImpl<JobExecution, String> implements JobExecutionRepository {
    public JobExecutionRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }


    @Override
    public Optional<JobExecution> findByJobId(String jobId) {
        SqlParams sqlParams = Queries.of(dbType).from(JobExecution.class).whereEq("jobId", jobId).single();
        JobExecution jobExecution = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
        return Optional.ofNullable(jobExecution);
    }
}
