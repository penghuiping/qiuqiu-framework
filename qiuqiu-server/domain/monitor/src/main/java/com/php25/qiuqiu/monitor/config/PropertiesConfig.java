package com.php25.qiuqiu.monitor.config;

import com.php25.qiuqiu.monitor.service.DictionaryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

/**
 * @author penghuiping
 * @date 2019/12/23 14:24
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class PropertiesConfig implements InitializingBean , ApplicationListener<RefreshScopeRefreshedEvent> {

    private final ConfigurableEnvironment configurableEnvironment;

    private final DictionaryService dictionaryService;


    @Override
    public void afterPropertiesSet() throws Exception {
        DictPropertySource dictPropertySource = new DictPropertySource("dict_map",dictionaryService);
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        mutablePropertySources.addLast(dictPropertySource);
    }

    @Override
    public void onApplicationEvent(RefreshScopeRefreshedEvent event) {
        log.info("开始刷新DictPropertySource...");
        DictPropertySource dictPropertySource = new DictPropertySource("dict_map",dictionaryService);
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        mutablePropertySources.replace("dict_map",dictPropertySource);
    }
}
