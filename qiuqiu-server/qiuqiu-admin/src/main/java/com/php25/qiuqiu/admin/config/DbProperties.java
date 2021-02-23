package com.php25.qiuqiu.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author penghuiping
 * @date 2021/2/3 10:39
 */
@Component
@ConfigurationProperties(
        prefix = "spring.datasource"
)
@Getter
@Setter
public class DbProperties {
    private String type;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private Integer minIdle;
    private Long connectionTimeout;

    private Long idleTimeout;
    private Long maxLifetime;
    private Integer maximumPoolSize;
    private String poolName;
}
