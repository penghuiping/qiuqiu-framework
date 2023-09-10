package com.php25.qiuqiu.monitor.service.impl;

import cn.easyes.core.biz.EsPageInfo;
import cn.easyes.core.conditions.select.LambdaEsQueryWrapper;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.monitor.copyer.SystemLogDtoCopyer;
import com.php25.qiuqiu.monitor.dao.es.SystemLogDao;
import com.php25.qiuqiu.monitor.dao.es.po.SystemLogPo;
import com.php25.qiuqiu.monitor.dto.SystemLogDto;
import com.php25.qiuqiu.monitor.entity.SystemLog;
import com.php25.qiuqiu.monitor.repository.SystemLogRepository;
import com.php25.qiuqiu.monitor.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2023/9/10 19:02
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogDao systemLogDao;

    private final SystemLogRepository systemLogRepository;

    private final SystemLogDtoCopyer systemLogDtoCopyer;

    @Bean
    public Consumer<String> logs() {
        return message -> {
            System.out.println(message);
            SystemLogPo systemLogPo = JsonUtil.fromJson(message, SystemLogPo.class);
            systemLogDao.insert(systemLogPo);
        };

    }

    @Override
    public PageDto<SystemLogDto> page(String keywords, Integer pageNum, Integer pageSize) {
        PageDto<SystemLog> pageDto = systemLogRepository.page(keywords, pageNum, pageSize);
        PageDto<SystemLogDto> res = new PageDto<>();
        res.setTotal(pageDto.getTotal());
        res.setData(pageDto.getData().stream().map(systemLogDtoCopyer::toDto).collect(Collectors.toList()));
        return res;
    }
}
