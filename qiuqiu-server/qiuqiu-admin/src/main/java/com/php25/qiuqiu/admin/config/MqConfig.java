package com.php25.qiuqiu.admin.config;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * @author penghuiping
 * @date 2021/3/11 11:40
 */

@EnableConfigurationProperties({RabbitProperties.class})
@Configuration
public class MqConfig {

//    @Bean
//    SubscribableChannel auditLogChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    BroadcastCapableChannel dictChannel() {
//        return new PublishSubscribeChannel();
//    }
//
//    @Bean
//    SubscribableChannel timerJobEnabledChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    SubscribableChannel timerJobDisabledChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    SubscribableChannel mergeStatisticLoadedJobExecutionChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    BroadcastCapableChannel statisticLoadedJobExecutionChannel() {
//        return new PublishSubscribeChannel();
//    }
//
//    @Bean
//    BroadcastCapableChannel wsSessionChannel() {
//        return new PublishSubscribeChannel();
//    }


//    @Profile(value = {"dev", "test"})
//    @Bean
//    RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
//
//    @Primary
//    @Profile(value = {"dev", "test"})
//    @Bean
//    CachingConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
//        RabbitConnectionFactoryBean rabbitConnectionFactoryBean = new RabbitConnectionFactoryBean();
//        rabbitConnectionFactoryBean.setHost(rabbitProperties.getHost());
//        rabbitConnectionFactoryBean.setPassword(rabbitProperties.getPassword());
//        rabbitConnectionFactoryBean.setPort(rabbitProperties.getPort());
//        rabbitConnectionFactoryBean.setVirtualHost(rabbitProperties.getHost());
//        return new CachingConnectionFactory(rabbitConnectionFactoryBean.getRabbitConnectionFactory());
//    }
}
