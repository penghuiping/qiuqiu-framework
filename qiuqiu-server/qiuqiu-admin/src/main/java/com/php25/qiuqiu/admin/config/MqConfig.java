package com.php25.qiuqiu.admin.config;


import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.rabbit.config.RabbitBinderConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author penghuiping
 * @date 2021/3/11 11:40
 */

@EnableConfigurationProperties({RabbitProperties.class})
@Import({RabbitBinderConfiguration.class})
@Configuration
public class MqConfig {

}
