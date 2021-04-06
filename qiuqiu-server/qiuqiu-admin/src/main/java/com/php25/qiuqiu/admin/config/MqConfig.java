package com.php25.qiuqiu.admin.config;

import com.php25.common.mq.MessageQueueManager;
import com.php25.common.mq.rabbit.RabbitMessageListener;
import com.php25.common.mq.rabbit.RabbitMessageQueueManager;
import com.php25.common.mq.redis.RedisMessageQueueManager;
import com.php25.common.redis.RedisManager;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author penghuiping
 * @date 2021/3/11 11:40
 */

@Configuration
public class MqConfig {

    @Profile(value = {"local"})
    @Bean
    MessageQueueManager messageQueueManager0(RedisManager redisManager) {
        return new RedisMessageQueueManager(redisManager);
    }


    @Profile(value = {"dev","test"})
    @Bean
    MessageQueueManager messageQueueManager(RabbitTemplate rabbitTemplate) {
        return new RabbitMessageQueueManager(rabbitTemplate, new RabbitMessageListener(rabbitTemplate));
    }

}
