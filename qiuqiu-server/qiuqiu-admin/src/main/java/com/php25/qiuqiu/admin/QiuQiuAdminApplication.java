package com.php25.qiuqiu.admin;

import com.php25.common.config.EnableWeb;
import com.php25.common.redis.config.EnableRedisManager;
import com.php25.common.timer.config.EnableTimer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author penghuiping
 * @date 2021/2/3 10:28
 */
@SpringBootApplication(exclude = {
        ReactiveUserDetailsServiceAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RabbitAutoConfiguration.class,
//        ZipkinAutoConfiguration.class,
        DataSourceHealthContributorAutoConfiguration.class,
})
@EnableTransactionManagement
@EnableScheduling
@EnableWeb
@EnableTimer
@EnableRedisManager
@ComponentScan(basePackages = {"com.php25.qiuqiu","com.php25.common"})
public class QiuQiuAdminApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(QiuQiuAdminApplication.class, args);
    }
}
