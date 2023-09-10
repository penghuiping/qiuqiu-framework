package com.php25.qiuqiu.monitor.copyer;

import com.php25.qiuqiu.monitor.dto.SystemLogDto;
import com.php25.qiuqiu.monitor.entity.SystemLog;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2023/9/10 23:14
 */
@Mapper(componentModel = "spring")
public interface SystemLogDtoCopyer {
    SystemLogDto toDto(SystemLog systemLog);
}
