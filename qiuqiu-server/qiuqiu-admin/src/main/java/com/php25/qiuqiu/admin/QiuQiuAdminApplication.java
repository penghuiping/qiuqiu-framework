package com.php25.qiuqiu.admin;

import com.php25.common.ws.mq.WsChannelProcessor;
import com.php25.qiuqiu.job.mq.MergeStatisticLoadedJobProcessor;
import com.php25.qiuqiu.job.mq.StatisticLoadedJobProcessor;
import com.php25.qiuqiu.job.mq.TimeJobDisabledProcessor;
import com.php25.qiuqiu.job.mq.TimeJobEnabledProcessor;
import com.php25.qiuqiu.monitor.mq.AuditLogProcessor;
import com.php25.qiuqiu.monitor.mq.DictProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.cloud.sleuth.autoconfig.zipkin2.ZipkinAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
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
//        RabbitAutoConfiguration.class,
        ZipkinAutoConfiguration.class,
        DataSourceHealthContributorAutoConfiguration.class
})
@EnableTransactionManagement
@EnableScheduling
@EnableBinding({DictProcessor.class, AuditLogProcessor.class, MergeStatisticLoadedJobProcessor.class, StatisticLoadedJobProcessor.class, TimeJobDisabledProcessor.class, TimeJobEnabledProcessor.class, WsChannelProcessor.class})
@ComponentScan(basePackages = {"com.php25"})
public class QiuQiuAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(QiuQiuAdminApplication.class, args);
    }
}
