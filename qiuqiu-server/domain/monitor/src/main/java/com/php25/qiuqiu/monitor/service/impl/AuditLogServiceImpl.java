package com.php25.qiuqiu.monitor.service.impl;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.common.mq.Message;
import com.php25.common.mq.MessageQueueManager;
import com.php25.common.mq.MessageSubscriber;
import com.php25.common.mq.redis.RedisMessageSubscriber;
import com.php25.common.redis.RedisManager;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.model.AuditLog;
import com.php25.qiuqiu.monitor.repository.AuditLogRepository;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 14:32
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService, InitializingBean {

    private final MessageQueueManager messageQueueManager;

    private final IdGenerator idGenerator;

    private final ExecutorService executorService;

    private final RedisManager redisManager;

    private final AuditLogRepository auditLogRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        MessageSubscriber subscriber = new RedisMessageSubscriber(executorService, redisManager);
        subscriber.setHandler(message -> {
            log.info("msg body:{}", JsonUtil.toJson(message.getBody()));
            AuditLogDto auditLogDto = (AuditLogDto) message.getBody();
            this.create0(auditLogDto);
        });
        messageQueueManager.subscribe("audit_log", subscriber);
    }

    @Override
    public Boolean create(AuditLogDto auditLogDto) {
        Message message = new Message(idGenerator.getUUID(), "audit_log", auditLogDto);
        return messageQueueManager.send("audit_log", message);
    }

    private Boolean create0(AuditLogDto auditLogDto) {
        AuditLog auditLog = new AuditLog();
        BeanUtils.copyProperties(auditLogDto, auditLog);
        auditLog.setIsNew(true);
        auditLogRepository.save(auditLog);
        return true;
    }

    @Override
    public DataGridPageDto<AuditLogDto> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<AuditLog> page = auditLogRepository.findAll(SearchParamBuilder.builder(), pageRequest);
        DataGridPageDto<AuditLogDto> dataGrid = new DataGridPageDto<>();
        List<AuditLogDto> data = page.get().map(auditLog -> {
            AuditLogDto auditLogDto = new AuditLogDto();
            BeanUtils.copyProperties(auditLog, auditLogDto);
            return auditLogDto;
        }).collect(Collectors.toList());
        dataGrid.setData(data);
        dataGrid.setRecordsTotal(page.getTotalElements());
        return dataGrid;
    }
}
