package com.php25.qiuqiu.admin.config;

import cn.easyes.starter.config.EsAutoConfiguration;
import cn.easyes.starter.factory.IndexStrategyFactory;
import cn.easyes.starter.register.EsMapperScan;
import cn.easyes.starter.service.AutoProcessIndexService;
import cn.easyes.starter.service.impl.AutoProcessIndexSmoothlyServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author penghuiping
 * @date 2023/9/10 20:30
 */
@Import(EsAutoConfiguration.class)
@EsMapperScan("com.php25.qiuqiu.monitor.dao.es")
@Configuration
public class EsConfig {

    @Bean
    IndexStrategyFactory indexStrategyFactory(ApplicationContext applicationContext) {
        IndexStrategyFactory indexStrategyFactory = new IndexStrategyFactory();
        indexStrategyFactory.setApplicationContext(applicationContext);
        return indexStrategyFactory;
    }

    @Bean
    AutoProcessIndexService autoProcessIndexService() {
        return new AutoProcessIndexSmoothlyServiceImpl();
    }
}
