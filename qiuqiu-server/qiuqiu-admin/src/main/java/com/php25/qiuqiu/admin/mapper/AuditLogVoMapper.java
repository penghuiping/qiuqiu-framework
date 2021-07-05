package com.php25.qiuqiu.admin.mapper;

import com.php25.qiuqiu.admin.vo.out.AuditLogPageOutVo;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2021/7/2 09:07
 */
@Mapper(componentModel = "spring")
public interface AuditLogVoMapper {

    /**
     * dto转vo
     *
     * @param auditLogDto dto
     * @return vo
     */
    AuditLogPageOutVo toAuditLogPageOutVo(AuditLogDto auditLogDto);

}
