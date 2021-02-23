package com.php25.qiuqiu.admin.config;

import com.google.common.collect.Lists;
import com.php25.common.flux.web.APIVersionHandlerMapping;
import com.php25.common.flux.web.XssRequestBodyAdvice;
import com.php25.common.flux.web.XssSafeHtml;
import com.php25.qiuqiu.admin.interceptor.JwtAuthInterceptor;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.internal.constraintvalidators.hv.SafeHtmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor).addPathPatterns("/v1/user/**").excludePathPatterns("/v1/user/login");

    }

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        super.createRequestMappingHandlerMapping();
        return new APIVersionHandlerMapping();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
    }

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.createRequestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setRequestBodyAdvice(Lists.newArrayList(new XssRequestBodyAdvice() {
            @Override
            public SafeHtmlValidator initializeValidator() {
                SafeHtmlValidator safeHtmlValidator = new SafeHtmlValidator();
                safeHtmlValidator.initialize(new XssSafeHtml(SafeHtml.WhiteListType.BASIC, new String[]{}));
                return safeHtmlValidator;
            }
        }));
        return requestMappingHandlerAdapter;
    }
}