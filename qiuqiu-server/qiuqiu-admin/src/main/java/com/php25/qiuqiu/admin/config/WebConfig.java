package com.php25.qiuqiu.admin.config;

import com.google.common.collect.Lists;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.flux.web.WebLogFilter;
import com.php25.common.flux.web.XssRequestBodyAdvice;
import com.php25.common.flux.web.XssResponseBodyAdvice;
import com.php25.qiuqiu.admin.interceptor.JwtAuthInterceptor;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/2/3 10:29
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    JwtAuthInterceptor jwtAuthInterceptor;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login", "/user/refresh", "/user/img_code", "/loan/**");

    }


    @Bean
    FilterRegistrationBean<WebLogFilter> loggingFilter() {
        FilterRegistrationBean<WebLogFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new WebLogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
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
            private final Safelist whitelist = Safelist.simpleText();

            @Override
            public Safelist configWhiteList() {
                return this.whitelist;
            }
        }));
        requestMappingHandlerAdapter.setResponseBodyAdvice(Lists.newArrayList(new XssResponseBodyAdvice() {
            private final Safelist whitelist = Safelist.simpleText();

            @Override
            public Safelist configWhiteList() {
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
