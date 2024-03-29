package com.php25.common.timer.config;

import com.php25.common.redis.RedisManager;
import com.php25.common.timer.Timer;
import com.php25.common.timer.TimerInnerLogDbManager;
import com.php25.common.timer.TimerInnerLogManager;
import com.php25.common.timer.TimerInnerLogRedisManager;
import com.php25.common.timer.dao.TimerInnerLogDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

/**
 * @author penghuiping
 * @date 2022/7/24 18:02
 */
@AutoConfigureAfter(value = {RedisManager.class,TimerInnerLogDao.class})
@MapperScan(basePackages = {"com.php25.common.timer.dao"})
public class TimerAutoConfiguration {


    @Bean
    TimerInnerLogManager timerInnerLogManager(TimerInnerLogDao timerInnerLogDao, RedisManager redisManager) {
        return new TimerInnerLogDbManager(timerInnerLogDao);
    }

    @Bean
    Timer timer() {
        return new Timer();
    }
}
