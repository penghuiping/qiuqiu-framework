package com.php25.qiuqiu.monitor.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.TimeUtil;
import com.php25.qiuqiu.monitor.dao.db.AuditLogDao;
import com.php25.qiuqiu.monitor.dao.db.po.AuditLogPo;
import com.php25.qiuqiu.monitor.dao.db.view.AuditLogView;
import com.php25.qiuqiu.monitor.entity.AuditLog;
import com.php25.qiuqiu.monitor.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 15:09
 */
@RequiredArgsConstructor
@Repository
public class AuditLogRepositoryImpl implements AuditLogRepository {

    private final AuditLogDao auditLogDao;

    @Override
    public boolean save(AuditLog auditLog) {
        AuditLogPo auditLogPo = new AuditLogPo();
        BeanUtils.copyProperties(auditLog, auditLogPo);
        auditLogPo.setCreateTime(Date.from(auditLog.getCreateTime().toInstant(ZoneOffset.ofHours(8))));
        auditLogPo.setUpdateTime(Date.from(auditLog.getCreateTime().toInstant(ZoneOffset.ofHours(8))));
        auditLogPo.setCreateUser(auditLog.getUsername());
        auditLogPo.setUpdateUser(auditLog.getUsername());
        auditLogPo.setGroupId(StringUtil.isNotBlank(auditLog.getGroupId())?Long.parseLong(auditLog.getGroupId()):null);
        if (null == auditLog.getId()) {
            //新增
            return auditLogDao.insert(auditLogPo) > 0;
        } else {
            //更新
            return auditLogDao.updateById(auditLogPo) > 0;
        }
    }

    @Override
    public IPage<AuditLog> page(String username, Integer pageNum, Integer pageSize) {
        IPage<AuditLogView> iPage = auditLogDao.selectPageByUsername(new Page<>(pageNum, pageSize),username);
        IPage<AuditLog> result = new Page<>();
        List<AuditLog> auditLogList = iPage.getRecords().stream().map(auditLogPo -> {
            AuditLog auditLog = new AuditLog();
            BeanUtils.copyProperties(auditLogPo, auditLog);
            auditLog.setGroupId(auditLogPo.getGroupId()==null?"": auditLogPo.getGroupId().toString());
            auditLog.setCreateTime(TimeUtil.toLocalDateTime(auditLogPo.getCreateTime()));
            return auditLog;
        }).collect(Collectors.toList());
        result.setRecords(auditLogList);
        result.setTotal(iPage.getTotal());
        result.setCurrent(pageNum);
        result.setSize(pageSize);
        return result;
    }
}
