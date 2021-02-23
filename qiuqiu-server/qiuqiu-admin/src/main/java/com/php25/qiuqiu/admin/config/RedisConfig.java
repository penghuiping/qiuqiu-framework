package com.php25.qiuqiu.admin.config;

import com.php25.common.core.util.StringUtil;
import com.php25.common.redis.RedisManager;
import com.php25.common.redis.RedisManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisManager redisManager(@Autowired StringRedisTemplate stringRedisTemplate) {
        return new RedisManagerImpl(stringRedisTemplate);
    }


    @Profile(value = {"test","dev"})
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisProperties.Cluster clusterProperties = redisProperties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        return new LettuceConnectionFactory(config);
    }

    @Profile(value = {"local"})
    @Bean
    public LettuceConnectionFactory redisConnectionFactory1(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setDatabase(redisProperties.getDatabase());
        if (!StringUtil.isBlank(redisProperties.getPassword())) {
            config.setPassword(redisProperties.getPassword());
        }
        return new LettuceConnectionFactory(config);
    }
}
