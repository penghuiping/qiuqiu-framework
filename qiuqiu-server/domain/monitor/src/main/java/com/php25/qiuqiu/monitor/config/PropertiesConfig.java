package com.php25.qiuqiu.monitor.config;

import com.php25.qiuqiu.monitor.service.DictionaryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

/**
 * @author penghuiping
 * @date 2019/12/23 14:24
 */
@Configuration
public class PropertiesConfig implements InitializingBean {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private DictionaryService dictionaryService;


    @Override
    public void afterPropertiesSet() throws Exception {
        DictPropertySource dictPropertySource = new DictPropertySource("dict_map",dictionaryService);
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        mutablePropertySources.addLast(dictPropertySource);
    }
}
