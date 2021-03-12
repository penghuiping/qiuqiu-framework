package com.php25.qiuqiu.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author penghuiping
 * @date 2021/3/12 14:31
 */
@Configuration
public class HttpConfig {

    @Bean
    public RestTemplate restTemplate() {
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        okHttp3ClientHttpRequestFactory.setConnectTimeout(5000);
        okHttp3ClientHttpRequestFactory.setReadTimeout(5000);
        okHttp3ClientHttpRequestFactory.setWriteTimeout(5000);
        return new RestTemplate(okHttp3ClientHttpRequestFactory);
    }
}
