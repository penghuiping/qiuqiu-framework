package com.php25.qiuqiu.admin.config;

import brave.Tracing;
import com.google.common.collect.Sets;
import com.php25.common.core.util.StringUtil;
import com.php25.common.redis.RedisManager;
import com.php25.common.redis.impl.RedisManagerImpl;
import com.php25.common.redis.local.LocalRedisManager;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.tracing.BraveTracing;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@EnableConfigurationProperties({RedisProperties.class})
@Configuration
public class RedisConfig {
    @Profile(value = {"local"})
    @Bean
    public RedisManager redisManager1() {
        return new LocalRedisManager(1024);
    }

    @ConditionalOnMissingBean(ClientResources.class)
    @Bean(destroyMethod = "shutdown")
    ClientResources lettuceClientResources() {
        return DefaultClientResources.create().mutate()
                .tracing(BraveTracing.builder().serviceName("redis-lettuce").tracing(Tracing.current()).build()).build();
    }

    @Profile(value = {"dev","docker"})
    @Bean
    public RedisManager redisManager(StringRedisTemplate stringRedisTemplate) {
        return new RedisManagerImpl(stringRedisTemplate);
    }


    @Profile(value = {"dev","docker"})
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    @Profile(value = {"test"})
    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties,ClientResources lettuceClientResources) {
        RedisProperties.Cluster clusterProperties = redisProperties.getCluster();
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        if (redisProperties.getPassword() != null) {
            config.setPassword(redisProperties.getPassword());
        }

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();

        if (redisProperties.isSsl()) {
            builder.useSsl();
        }

        RedisProperties.Pool properties = redisProperties.getLettuce().getPool();
        LettuceClientConfiguration clientConfiguration = builder
                .commandTimeout((redisProperties.getTimeout()))
                .clientResources(lettuceClientResources)
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder().connectTimeout(redisProperties.getConnectTimeout()).build())
                        .timeoutOptions(TimeoutOptions.enabled()).build())
                .poolConfig(getPoolConfig(properties)).build();
        return new LettuceConnectionFactory(config, clientConfiguration);
    }


    @Profile(value = {"dev","docker"})
    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory redisConnectionFactory1(RedisProperties redisProperties,ClientResources lettuceClientResources) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setDatabase(redisProperties.getDatabase());
        if (!StringUtil.isBlank(redisProperties.getPassword())) {
            config.setPassword(redisProperties.getPassword());
        }

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder =
                LettucePoolingClientConfiguration.builder();

        if (redisProperties.isSsl()) {
            builder.useSsl();
        }

        RedisProperties.Pool properties = redisProperties.getLettuce().getPool();
        LettuceClientConfiguration clientConfiguration = builder
                .commandTimeout((redisProperties.getTimeout()))
                .clientResources(lettuceClientResources)
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder().connectTimeout(redisProperties.getConnectTimeout()).build())
                        .timeoutOptions(TimeoutOptions.enabled()).build())
                .poolConfig(getPoolConfig(properties)).build();
        return new LettuceConnectionFactory(config, clientConfiguration);
    }

    @Profile(value = {"product"})
    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory redisConnectionFactory2(RedisProperties redisProperties,ClientResources lettuceClientResources) {
        RedisProperties.Sentinel sentinelProperties = redisProperties.getSentinel();
        RedisSentinelConfiguration config = new RedisSentinelConfiguration(
                sentinelProperties.getMaster(), Sets.newHashSet(sentinelProperties.getNodes()));

        if (sentinelProperties.getPassword() != null) {
            config.setSentinelPassword(sentinelProperties.getPassword());
        }

        if (redisProperties.getPassword() != null) {
            config.setPassword(redisProperties.getPassword());
        }

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();

        if (redisProperties.isSsl()) {
            builder.useSsl();
        }

        RedisProperties.Pool properties = redisProperties.getLettuce().getPool();
        LettuceClientConfiguration clientConfiguration = builder
                .commandTimeout((redisProperties.getTimeout()))
                .clientResources(lettuceClientResources)
                .clientOptions(ClientOptions.builder()
                        .socketOptions(SocketOptions.builder().connectTimeout(redisProperties.getConnectTimeout()).build())
                                .timeoutOptions(TimeoutOptions.enabled()).build())
                .poolConfig(getPoolConfig(properties)).build();
        return new LettuceConnectionFactory(config, clientConfiguration);
    }

    private GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool properties) {
        GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(properties.getMaxActive());
        config.setMaxIdle(properties.getMaxIdle());
        config.setMinIdle(properties.getMinIdle());
        if (properties.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
        }
        if (properties.getMaxWait() != null) {
            config.setMaxWaitMillis(properties.getMaxWait().toMillis());
        }
        return config;
    }
}
