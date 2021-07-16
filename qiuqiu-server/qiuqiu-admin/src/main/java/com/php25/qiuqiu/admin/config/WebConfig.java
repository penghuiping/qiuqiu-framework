package com.php25.qiuqiu.admin.config;

import com.google.common.collect.Lists;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.flux.web.APIVersionHandlerMapping;
import com.php25.common.flux.web.XssRequestBodyAdvice;
import com.php25.qiuqiu.admin.interceptor.JwtAuthInterceptor;
import com.php25.qiuqiu.admin.interceptor.LogInterceptor;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    JwtAuthInterceptor jwtAuthInterceptor;

    @Autowired
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/v1/**/**");
        registry.addInterceptor(jwtAuthInterceptor).addPathPatterns("/v1/**/**").excludePathPatterns("/v1/user/login","/v1/user/refresh","/v1/user/img_code","/v1/loan/**");

    }

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        super.createRequestMappingHandlerMapping();
        return new APIVersionHandlerMapping();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(JsonUtil.getObjectMapper()));
        converters.add(new StringHttpMessageConverter());
    }

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.createRequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setRequestBodyAdvice(Lists.newArrayList(new XssRequestBodyAdvice() {
            private final Whitelist whitelist = Whitelist.simpleText();
            @Override
            public Whitelist configWhiteList() {
                return this.whitelist;
            }
        }));
        return requestMappingHandlerAdapter;
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }
}
