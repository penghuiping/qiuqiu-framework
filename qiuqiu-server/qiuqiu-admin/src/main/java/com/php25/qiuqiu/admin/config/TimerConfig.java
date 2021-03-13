package com.php25.qiuqiu.admin.config;

import com.php25.common.timer.Timer;
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
}
