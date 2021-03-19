package com.php25.qiuqiu.admin.config;

import com.php25.common.db.DbType;
import com.php25.common.redis.RedisManager;
import com.php25.common.timer.TimerInnerLogManager;
import com.php25.common.timer.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author penghuiping
 * @date 2021/3/13 17:14
 */
@Configuration
public class TimerConfig {

    @Bean
    Timer timer() {
        return new Timer();
    }

    @Bean
    TimerInnerLogManager jobExecutionLogManager(DbType dbType, JdbcTemplate jdbcTemplate, RedisManager redisManager) {
        return new TimerInnerLogManager(dbType, jdbcTemplate,redisManager);
    }
}
