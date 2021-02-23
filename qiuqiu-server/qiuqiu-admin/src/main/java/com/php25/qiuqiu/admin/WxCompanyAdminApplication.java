package com.php25.qiuqiu.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author penghuiping
 * @date 2021/2/3 10:28
 */
@SpringBootApplication(exclude = {
        ReactiveUserDetailsServiceAutoConfiguration.class
})
@EnableTransactionManagement
@EnableScheduling
@ComponentScan(basePackages = {"com.php25"})
public class WxCompanyAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxCompanyAdminApplication.class, args);
    }
}
