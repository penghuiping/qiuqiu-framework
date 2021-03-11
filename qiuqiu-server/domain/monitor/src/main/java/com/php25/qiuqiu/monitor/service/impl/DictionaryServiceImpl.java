package com.php25.qiuqiu.monitor.service.impl;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.StringUtil;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.common.mq.Message;
import com.php25.common.mq.MessageQueueManager;
import com.php25.common.mq.MessageSubscriber;
import com.php25.common.mq.redis.RedisMessageSubscriber;
import com.php25.common.redis.RedisManager;
import com.php25.qiuqiu.monitor.dto.DictDto;
import com.php25.qiuqiu.monitor.model.Dict;
import com.php25.qiuqiu.monitor.repository.DictRepository;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 16:22
 */
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService, InitializingBean {

    private final DictRepository dictRepository;

    private final Map<String, DictDto> cache = new ConcurrentHashMap<>(256);

    private final MessageQueueManager messageQueueManager;

    private final IdGenerator idGenerator;
    private final ExecutorService executorService;
    private final RedisManager redisManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageSubscriber messageSubscriber = new RedisMessageSubscriber(executorService, redisManager);
        messageSubscriber.setHandler(message -> {
            String key = message.getBody().toString();
            this.removeCache0(key);
        });
        messageQueueManager.subscribe("dict", messageSubscriber);
    }

    @Override
    public String get(String key) {
        DictDto value = cache.get(key);
        if (null == value) {
            Optional<Dict> dictOptional = dictRepository.findByKey(key);
            if (!dictOptional.isPresent()) {
                return null;
            }
            Dict dict = dictOptional.get();
            DictDto dictDto = new DictDto();
            BeanUtils.copyProperties(dict, dictDto);
            value = dictDto;
            cache.put(key, value);
        }

        if (!value.getEnable()) {
            return null;
        }
        return value.getValue();
    }

    @Override
    public Boolean update(DictDto dictDto) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictDto, dict);
        dict.setIsNew(false);
        dictRepository.save(dict);
        return true;
    }

    @Override
    public Boolean create(String key, String value, String description) {
        Dict dict = new Dict();
        dict.setKey(key);
        dict.setValue(value);
        dict.setDescription(description);
        dict.setEnable(true);
        dict.setIsNew(true);
        dictRepository.save(dict);
        return true;
    }

    @Override
    public Boolean removeCache(String key) {
        Message message = new Message(idGenerator.getUUID(), "dict", key);
        return messageQueueManager.send("dict", message);
    }

    @Override
    public Boolean delete(String key) {
        return dictRepository.deleteByKey(key);
    }

    @Override
    public DataGridPageDto<DictDto> page(String key, Integer pageNum, Integer pageSize) {
        SearchParamBuilder searchParamBuilder = SearchParamBuilder.builder();
        if (!StringUtil.isBlank(key)) {
            searchParamBuilder.append(SearchParam.of("key", Operator.EQ, key));
        }
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<Dict> page = dictRepository.findAll(searchParamBuilder, pageRequest);
        DataGridPageDto<DictDto> dataGrid = new DataGridPageDto<>();
        List<DictDto> dictDtoList = page.get().map(dict -> {
            DictDto dictDto = new DictDto();
            BeanUtils.copyProperties(dict, dictDto);
            return dictDto;
        }).collect(Collectors.toList());
        dataGrid.setData(dictDtoList);
        dataGrid.setRecordsTotal(page.getTotalElements());
        return dataGrid;
    }

    private boolean removeCache0(String key) {
        this.cache.remove(key);
        return true;
    }
}
