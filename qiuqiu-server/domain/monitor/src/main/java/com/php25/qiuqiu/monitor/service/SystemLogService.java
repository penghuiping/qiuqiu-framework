package com.php25.qiuqiu.monitor.service;

import com.php25.common.core.dto.PageDto;
import com.php25.qiuqiu.monitor.dto.SystemLogDto;

/**
 * @author penghuiping
 * @date 2023/9/10 19:02
 */
public interface SystemLogService {

    /**
     * 分页搜索获取系统日志
     *
     * @param keywords 搜索关键字
     * @param pageNum 当前页
     * @param pageSize 每页多少条
     * @return 系统日志分页数据
     */
    PageDto<SystemLogDto> page(String keywords, Integer pageNum, Integer pageSize);
}
