package com.php25.qiuqiu.monitor.service;

import com.php25.common.core.dto.PageDto;
import com.php25.qiuqiu.monitor.dto.AuditLogDto;

/**
 * 系统用户操作审计日志
 *
 * @author penghuiping
 * @date 2021/3/1 20:46
 */
public interface AuditLogService {

    /**
     * 往数据库插入审计日志(注: 此方法先要把日志信息存入mq,以降低数据库写入压力)
     *
     * @param auditLogDto 审计日志信息
     * @return true:创建成功
     */
    Boolean create(AuditLogDto auditLogDto);

    /**
     * 审计日志分页查询
     *
     * @param username 用户名搜索
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @return 分页列表
     */
    PageDto<AuditLogDto> page(String username, Integer pageNum, Integer pageSize);
}
