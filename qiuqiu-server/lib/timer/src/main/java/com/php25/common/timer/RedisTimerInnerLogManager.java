package com.php25.common.timer;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.SpringContextHolder;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.redis.RedisManager;
import com.php25.common.timer.entity.TimerInnerLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.locks.Lock;

/**
 * @author penghuiping
 * @date 2021/3/19 10:53
 */
public class RedisTimerInnerLogManager implements TimerInnerLogManager {
    private static final Logger log = LoggerFactory.getLogger(RedisTimerInnerLogManager.class);

    private final DbType dbType;

    private final JdbcTemplate jdbcTemplate;

    private final RedisManager redisManager;

    public RedisTimerInnerLogManager(DbType dbType, JdbcTemplate jdbcTemplate, RedisManager redisManager) {
        this.dbType = dbType;
        this.jdbcTemplate = jdbcTemplate;
        this.redisManager = redisManager;
    }

    @Override
    public void synchronizedExecutionJob(Job task) {
        Lock lock = redisManager.lock(task.getJobExecutionId() + ":" + task.getExecuteTime());
        String cron = task.getCron();
        String executionId = task.getJobExecutionId();
        Long executionTime = task.getExecuteTime();
        SqlParams sqlParams = Queries.of(dbType).from(TimerInnerLog.class)
                .whereEq("id", executionId)
                .andEq("executionTime", executionTime).single();
        sqlParams.setSql(sqlParams.getSql());
        TimerInnerLog jobExecutionLog = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
        if (null != jobExecutionLog && jobExecutionLog.getStatus() == 0) {
            lock.lock();
            try {
                jobExecutionLog = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
                if (jobExecutionLog.getStatus() == 0) {
                    //job???????????????????????????????????????
                    LoggerFactory.getLogger(RedisTimerInnerLogManager.class).info("????????????:{}:{}", executionId, task.getExecuteTime());
                    task.getTask().run();
                    //??????????????????????????????
                    TimerInnerLog jobExecutionLog0 = new TimerInnerLog();
                    jobExecutionLog0.setId(jobExecutionLog.getId());
                    jobExecutionLog0.setExecutionTime(jobExecutionLog.getExecutionTime());
                    jobExecutionLog0.setStatus(1);
                    update(jobExecutionLog0);
                }
            } finally {
                lock.unlock();
            }
        }

        //???????????????????????????,?????????timer?????????
        Timer timer = SpringContextHolder.getBean0(Timer.class);
        timer.removeCache(executionId);

        //??????????????????????????????????????????????????????job
        if (StringUtil.isBlank(cron)) {
            return;
        }
        Date date = null;
        try {
            date = new CronExpression(cron).getNextValidTimeAfter(new Date());
        } catch (ParseException e) {
            throw Exceptions.throwIllegalStateException("cron?????????????????????", e);
        }
        if (null == date) {
            return;
        }
        Job job = new Job(executionId, cron, task.getTask());
        timer.add(job, true);
    }

    @Override
    public void create(TimerInnerLog jobExecutionLog) {
        Lock lock = redisManager.lock(jobExecutionLog.getId() + ":" + jobExecutionLog.getExecutionTime());
        SqlParams sqlParams0 = Queries.of(dbType).from(TimerInnerLog.class)
                .whereEq("id", jobExecutionLog.getId())
                .andEq("executionTime", jobExecutionLog.getExecutionTime())
                .single();
        sqlParams0.setSql(sqlParams0.getSql());
        TimerInnerLog jobExecutionLog0 = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams0);
        if (null == jobExecutionLog0) {
            lock.lock();
            try {
                jobExecutionLog0 = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams0);
                if (null == jobExecutionLog0) {
                    //??????????????????
                    log.info("???????????????:{}", JsonUtil.toJson(jobExecutionLog));
                    SqlParams sqlParams = Queries.of(dbType).from(TimerInnerLog.class).insert(jobExecutionLog);
                    QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).insert(sqlParams);
                } else {
                    log.info("?????????:{}", JsonUtil.toJson(jobExecutionLog));
                }
            } finally {
                lock.unlock();
            }
        } else {
            log.info("?????????:{}", JsonUtil.toJson(jobExecutionLog));
        }
    }

    @Override
    public void update(TimerInnerLog jobExecutionLog) {
        TimerInnerLog timerInnerLog = new TimerInnerLog();
        timerInnerLog.setStatus(jobExecutionLog.getStatus());
        SqlParams sqlParams = Queries.of(dbType).from(TimerInnerLog.class)
                .whereEq("id", jobExecutionLog.getId())
                .andEq("executionTime", jobExecutionLog.getExecutionTime())
                .update(timerInnerLog);
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).update(sqlParams);
    }
}
