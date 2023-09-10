package com.php25.qiuqiu.monitor.repository;

import com.php25.common.core.dto.PageDto;
import com.php25.qiuqiu.monitor.dto.SystemLogDto;
import com.php25.qiuqiu.monitor.entity.SystemLog;

/**
 * @author penghuiping
 * @date 2023/9/10 23:16
 */
public interface SystemLogRepository {

    /**
     * 分页搜索获取系统日志
     *
     * @param keywords 搜索关键字
     * @param pageNum 当前页
     * @param pageSize 每页多少条
     * @return 系统日志分页数据
     */
    PageDto<SystemLog> page(String keywords, Integer pageNum, Integer pageSize);
}
