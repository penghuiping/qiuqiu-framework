package com.php25.qiuqiu.admin.config;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
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

/**
 * @author penghuiping
 * @date 2021/3/12 14:31
 */
@Log4j2
@Configuration
public class HttpConfig {

    @Bean
    public RestTemplate restTemplate() {
        OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
        okHttp3ClientHttpRequestFactory.setConnectTimeout(5000);
        okHttp3ClientHttpRequestFactory.setReadTimeout(5000);
        okHttp3ClientHttpRequestFactory.setWriteTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory);
        restTemplate.setMessageConverters(Lists.newArrayList(new StringHttpMessageConverter(), new MappingJackson2HttpMessageConverter()));
        restTemplate.setInterceptors(Lists.newArrayList((httpRequest, body, clientHttpRequestExecution) -> {
            HttpHeaders headers = httpRequest.getHeaders();
            log.info("headers:{}", headers.toString());
            log.info("body:{}", new String(body, Charsets.UTF_8));
            return clientHttpRequestExecution.execute(httpRequest, body);
        }));
        return restTemplate;
    }
}
