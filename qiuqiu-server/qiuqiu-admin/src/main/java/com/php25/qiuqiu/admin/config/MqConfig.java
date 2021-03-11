package com.php25.qiuqiu.admin.config;

import com.php25.common.mq.MessageQueueManager;
import com.php25.common.mq.redis.RedisMessageQueueManager;
import com.php25.common.redis.RedisManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author penghuiping
 * @date 2021/3/11 11:40
 */

@Configuration
public class MqConfig {

    @Bean
    MessageQueueManager messageQueueManager(RedisManager redisManager) {
        return new RedisMessageQueueManager(redisManager);
    }
}
