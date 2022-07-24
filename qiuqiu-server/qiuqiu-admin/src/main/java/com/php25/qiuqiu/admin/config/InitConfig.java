package com.php25.qiuqiu.admin.config;

import com.php25.qiuqiu.admin.interceptor.JwtAuthInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author penghuiping
 * @date 2022/3/26 16:34
 */
@Slf4j
@Configuration
public class InitConfig extends WebMvcConfigurationSupport implements ApplicationRunner {


    @Autowired
    JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        jwtAuthInterceptor.setExcludeUris(new String[]{"/**/user/info","/**/other/logout","/**/other/get_init_config"});
        registry.addInterceptor(jwtAuthInterceptor).addPathPatterns("/**").excludePathPatterns("/other/login", "/other/refresh", "/other/img_code");
    }
}
