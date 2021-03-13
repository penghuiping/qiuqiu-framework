package com.php25.qiuqiu.admin.config;

import com.php25.common.db.DbType;
import com.php25.common.mq.MessageQueueManager;
import com.php25.common.redis.RedisManager;
import com.php25.common.timer.Timer;
import com.php25.qiuqiu.job.manager.JobManager;
import com.php25.qiuqiu.job.manager.RedisJobManager;
import com.php25.qiuqiu.job.repository.JobModelRepository;
import com.php25.qiuqiu.job.repository.JobModelRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.ExecutorService;

/**
 * @author penghuiping
 * @date 2021/3/13 17:14
 */
@Configuration
public class TimerConfig {

    @Bean
    JobManager jobManager(JobModelRepository jobModelRepository
            , MessageQueueManager messageQueueManager
            , RedisManager redisManager
            , ExecutorService pool) {
        return new RedisJobManager(jobModelRepository, messageQueueManager, redisManager, pool);
    }

    @Bean
    Timer timer() {
        return new Timer();
    }
}
