package com.php25.qiuqiu.admin.copyer;

import com.php25.qiuqiu.admin.vo.out.SystemLogPageOutVo;
import com.php25.qiuqiu.monitor.dto.SystemLogDto;
import org.mapstruct.Mapper;

/**
 * @author penghuiping
 * @date 2023/9/10 22:53
 */
@Mapper(componentModel = "spring")
public interface SystemLogVoCopyer {

    SystemLogPageOutVo toSystemLogPageOutVo(SystemLogDto systemLogDto);
}
