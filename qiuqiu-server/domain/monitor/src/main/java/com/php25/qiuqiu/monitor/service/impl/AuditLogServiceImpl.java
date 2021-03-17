package com.php25.qiuqiu.monitor.service.impl;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.common.mq.Message;
import com.php25.common.mq.MessageQueueManager;
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

    private final AuditLogRepository auditLogRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageQueueManager.subscribe("audit_log", message -> {
            String body = JsonUtil.toJson(message.getBody());
            log.info("msg body:{}", body);
            AuditLogDto auditLogDto =  JsonUtil.fromJson(body,AuditLogDto.class);
            this.create0(auditLogDto);
        });
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
    public DataGridPageDto<AuditLogDto> page(String username,Integer pageNum, Integer pageSize) {
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if(!StringUtil.isBlank(username)) {
            builder.append(SearchParam.of("username", Operator.EQ,username));
        }
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<AuditLog> page = auditLogRepository.findAll(builder, pageRequest);
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
