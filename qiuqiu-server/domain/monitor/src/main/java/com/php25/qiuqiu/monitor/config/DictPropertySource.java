package com.php25.qiuqiu.monitor.config;

import com.php25.qiuqiu.monitor.service.DictionaryService;
import org.springframework.core.env.PropertySource;


/**
 * @author penghuiping
 * @date 2019/12/23 14:26
 */
public class DictPropertySource extends PropertySource<DictionaryService> {

    public DictPropertySource(String name, DictionaryService source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }
}
