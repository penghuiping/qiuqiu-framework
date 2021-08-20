package com.php25.qiuqiu.admin.config;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.php25.common.core.util.JsonUtil;
import lombok.extern.log4j.Log4j2;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author penghuiping
 * @date 2021/3/12 14:31
 */
@Log4j2
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
}
