package com.php25.qiuqiu.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.util.JsonUtil;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.entity.AuditLog;
import com.php25.qiuqiu.monitor.copyer.AuditLogDtoCopyer;
import com.php25.qiuqiu.monitor.repository.AuditLogRepository;
import com.php25.qiuqiu.monitor.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 14:32
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    private final AuditLogDtoCopyer auditLogDtoMapper;

    private final StreamBridge streamBridge;

    @Bean
    Consumer<Message<AuditLogDto>> auditLogChannel() {
        return message->{
            log.info("msg body:{}", JsonUtil.toJson(message.getPayload()));
            AuditLogDto auditLogDto = message.getPayload();
            this.create0(auditLogDto);
        };
    }

    @Override
    public Boolean create(AuditLogDto auditLogDto) {
        Message<AuditLogDto> message = new GenericMessage<AuditLogDto>(auditLogDto);
        streamBridge.send("auditLogChannel-out-0",message);
        return true;
    }

    private Boolean create0(AuditLogDto auditLogDto) {
        AuditLog auditLog = auditLogDtoMapper.toEntity(auditLogDto);
        return auditLogRepository.save(auditLog);
    }

    @Override
    public PageDto<AuditLogDto> page(String username, Integer pageNum, Integer pageSize) {
        IPage<AuditLog> page = auditLogRepository.page(username, pageNum, pageSize);
        PageDto<AuditLogDto> dataGrid = new PageDto<>();
        List<AuditLogDto> data = page.getRecords().stream().map(auditLogDtoMapper::toDto)
                .collect(Collectors.toList());
        dataGrid.setData(data);
        dataGrid.setTotal(page.getTotal());
        return dataGrid;
    }
}
