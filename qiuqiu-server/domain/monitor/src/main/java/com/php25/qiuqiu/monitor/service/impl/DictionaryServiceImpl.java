package com.php25.qiuqiu.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.util.JsonUtil;
import com.php25.qiuqiu.monitor.dto.DictDto;
import com.php25.qiuqiu.monitor.entity.Dict;
import com.php25.qiuqiu.monitor.copyer.DictDtoCopyer;
import com.php25.qiuqiu.monitor.repository.DictRepository;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 16:22
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService, DisposableBean {

    private final DictRepository dictRepository;

    private final Map<String, DictDto> cache = new ConcurrentHashMap<>(256);

    private final StreamBridge streamBridge;

    private final DictDtoCopyer dictDtoMapper;

    @Value("${server.id}")
    private String serverId;


    @Bean
    Consumer<Message<String>> dictChannel() {
        return message -> {
            log.info("刷新缓存:{}", JsonUtil.toJson(message));
            String key = message.getPayload();
            this.removeCache0(key);
        };
    }

    @Override
    public void destroy() throws Exception {

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
            value = dictDtoMapper.toDto(dict);
            cache.put(key, value);
        }

        if (!value.getEnable()) {
            return null;
        }
        return value.getValue();
    }

    @Override
    public Boolean update(DictDto dictDto) {
        Dict dict = dictDtoMapper.toEntity(dictDto);
        return dictRepository.save(dict);
    }

    @Override
    public Boolean create(String key, String value, String description) {
        Dict dict = new Dict();
        dict.setKey(key);
        dict.setValue(value);
        dict.setDescription(description);
        dict.setEnable(true);
        return dictRepository.save(dict);
    }

    @Override
    public Boolean removeCache(String key) {
        Message<String> message = new GenericMessage<>(key);
        return streamBridge.send("dictChannel-out-0",message);
    }

    @Override
    public Boolean delete(String key) {
        return dictRepository.deleteByKey(key);
    }

    @Override
    public PageDto<DictDto> page(String key, Integer pageNum, Integer pageSize) {
        IPage<Dict> page = dictRepository.page(key, pageNum, pageSize);
        PageDto<DictDto> dataGrid = new PageDto<>();
        List<DictDto> dictDtoList = page.getRecords().stream().map(dictDtoMapper::toDto).collect(Collectors.toList());
        dataGrid.setData(dictDtoList);
        dataGrid.setTotal(page.getTotal());
        return dataGrid;
    }

    private boolean removeCache0(String key) {
        this.cache.remove(key);
        return true;
    }

    @Override
    public List<DictDto> getAllInitConfig() {
        List<Dict> list = dictRepository.findAllLikeRightKey("init");
        if(list.isEmpty()) {
            return Lists.newArrayList();
        }
        return list.stream().map(dict -> {
            DictDto dictDto = new DictDto();
            BeanUtils.copyProperties(dict,dictDto);
            return dictDto;
        }).collect(Collectors.toList());
    }
}
