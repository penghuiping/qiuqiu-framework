package com.php25.qiuqiu.monitor.copyer;

import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import com.php25.qiuqiu.monitor.entity.AuditLog;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/5 13:54
 */
@Mapper(componentModel = "spring")
public interface AuditLogDtoCopyer {

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
