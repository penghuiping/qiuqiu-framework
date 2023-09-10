package com.php25.qiuqiu.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.php25.common.db.DataPermission;
import com.php25.qiuqiu.monitor.dao.po.AuditLogPo;
import com.php25.qiuqiu.monitor.dao.view.AuditLogView;
import com.php25.qiuqiu.monitor.entity.AuditLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author penghuiping
 * @date 2022/2/27 14:11
 */
public interface AuditLogDao extends BaseMapper<AuditLogPo> {


    @DataPermission
    IPage<AuditLogView> selectPageByUsername(@Param("page") IPage<AuditLogPo> page, @Param("username") String username);
}
