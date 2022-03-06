package com.php25.qiuqiu.monitor.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.monitor.entity.AuditLog;

/**
 * @author penghuiping
 * @date 2021/3/11 15:06
 */
public interface AuditLogRepository {

    boolean save(AuditLog auditLog);

    IPage<AuditLog> page(String username, Integer pageNum, Integer pageSize);
}
