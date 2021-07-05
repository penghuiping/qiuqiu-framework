package com.php25.qiuqiu.monitor.mapper;

import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.entity.AuditLog;

/**
 * @author penghuiping
 * @date 2021/7/5 13:54
 */
public interface AuditLogDtoMapper {

    /**
     * dto转entity
     *
     * @param dto dto
     * @return entity
     */
    AuditLog toEntity(AuditLogDto dto);

    /**
     * entity转dto
     *
     * @param entity entity
     * @return dto
     */
    AuditLogDto toDto(AuditLog entity);
}
