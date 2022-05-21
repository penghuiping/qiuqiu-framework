package com.php25.qiuqiu.admin.config;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.php25.common.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 * @author penghuiping
 * @date 2021/3/12 14:31
 */
@Slf4j
@Configuration
public class HttpConfig {

    @Bean
    public RestTemplate restTemplate(
            @Value("${httpClient.maxIdleConnection:10}") Integer maxIdleConnection,
            @Value("${httpClient.connectionTimeout:5000}") Integer connectionTimeout,
            @Value("${httpClient.readTimeout:5000}") Integer readTimeout,
            @Value("${httpClient.writeTimeout:5000}") Integer writeTimeout
    ) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectionPool(new ConnectionPool(maxIdleConnection, 5, TimeUnit.MINUTES)).build();
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        okHttp3ClientHttpRequestFactory.setConnectTimeout(connectionTimeout);
        okHttp3ClientHttpRequestFactory.setReadTimeout(readTimeout);
        okHttp3ClientHttpRequestFactory.setWriteTimeout(writeTimeout);
        RestTemplate restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory);
        restTemplate.setMessageConverters(Lists.newArrayList(new StringHttpMessageConverter(), new MappingJackson2HttpMessageConverter(JsonUtil.getObjectMapper())));
        restTemplate.setInterceptors(Lists.newArrayList((httpRequest, body, clientHttpRequestExecution) -> {
            HttpHeaders headers = httpRequest.getHeaders();
            log.info("headers:{}", headers.toString());
            log.info("body:{}", new String(body, Charsets.UTF_8));
            return clientHttpRequestExecution.execute(httpRequest, body);
        }));
        return restTemplate;
    }

    @Bean
    RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate =  new RetryTemplate();
        final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(4);
        final NeverRetryPolicy neverRetryPolicy = new NeverRetryPolicy();
        ExceptionClassifierRetryPolicy retryPolicy = new ExceptionClassifierRetryPolicy();
        retryPolicy.setExceptionClassifier((Classifier<Throwable, RetryPolicy>) classifiable -> {
            if (classifiable.getCause() instanceof SocketTimeoutException) {
                return simpleRetryPolicy;
            }
            //不执行直接走 RecoveryCallback。recover（）
            return neverRetryPolicy;
        });
        retryTemplate.setRetryPolicy(retryPolicy);
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(1000);
        exponentialBackOffPolicy.setMultiplier(2);
        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);
        retryTemplate.setThrowLastExceptionOnExhausted(true);
        return retryTemplate;
    }
}
