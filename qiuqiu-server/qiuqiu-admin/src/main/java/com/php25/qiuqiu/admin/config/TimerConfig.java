package com.php25.qiuqiu.admin.config;

import com.php25.common.redis.RedisManager;
import com.php25.common.timer.Timer;
import com.php25.common.timer.TimerInnerLogManager;
import com.php25.common.timer.dao.TimerInnerLogDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    TimerInnerLogManager jobExecutionLogManager(TimerInnerLogDao timerInnerLogDao, RedisManager redisManager) {
        return new TimerInnerLogManager(timerInnerLogDao, redisManager);
    }
}
