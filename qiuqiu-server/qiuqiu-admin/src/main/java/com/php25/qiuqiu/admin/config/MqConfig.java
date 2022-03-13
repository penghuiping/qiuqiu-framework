package com.php25.qiuqiu.admin.config;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.BroadcastCapableChannel;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.core.MessagingTemplate;

/**
 * @author penghuiping
 * @date 2021/3/11 11:40
 */

@EnableConfigurationProperties({RabbitProperties.class})
@Configuration
public class MqConfig {

    @Bean
    MessagingTemplate messagingTemplate() {
        return new MessagingTemplate();
    }

    @Bean
    DirectChannel auditLogChannel() {
        return new DirectChannel();
    }

    @Bean
    BroadcastCapableChannel dictChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    DirectChannel timerJobEnabledChannel() {
        return new DirectChannel();
    }

    @Bean
    DirectChannel timerJobDisabledChannel() {
        return new DirectChannel();
    }

    @Bean
    DirectChannel mergeStatisticLoadedJobExecutionChannel() {
        return new DirectChannel();
    }

    @Bean
    PublishSubscribeChannel statisticLoadedJobExecutionChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    PublishSubscribeChannel wsSessionChannel() {
        return new PublishSubscribeChannel();
    }


    @Profile(value = {"dev", "test"})
    @Bean
    RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Profile(value = {"dev", "test"})
    @Bean
    CachingConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        RabbitConnectionFactoryBean rabbitConnectionFactoryBean = new RabbitConnectionFactoryBean();
        rabbitConnectionFactoryBean.setHost(rabbitProperties.getHost());
        rabbitConnectionFactoryBean.setPassword(rabbitProperties.getPassword());
        rabbitConnectionFactoryBean.setPort(rabbitProperties.getPort());
        rabbitConnectionFactoryBean.setVirtualHost(rabbitProperties.getHost());
        return new CachingConnectionFactory(rabbitConnectionFactoryBean.getRabbitConnectionFactory());
    }
}
