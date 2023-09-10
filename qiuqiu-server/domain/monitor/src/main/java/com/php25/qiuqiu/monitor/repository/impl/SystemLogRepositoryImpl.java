package com.php25.qiuqiu.monitor.repository.impl;

import cn.easyes.core.biz.EsPageInfo;
import cn.easyes.core.conditions.select.LambdaEsQueryWrapper;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.monitor.dao.es.SystemLogDao;
import com.php25.qiuqiu.monitor.dao.es.po.SystemLogPo;
import com.php25.qiuqiu.monitor.dto.SystemLogDto;
import com.php25.qiuqiu.monitor.entity.SystemLog;
import com.php25.qiuqiu.monitor.repository.SystemLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2023/9/10 23:16
 */
@RequiredArgsConstructor
@Repository
public class SystemLogRepositoryImpl implements SystemLogRepository {

    private final SystemLogDao systemLogDao;

    @Override
    public PageDto<SystemLog> page(String keywords, Integer pageNum, Integer pageSize) {
        LambdaEsQueryWrapper<SystemLogPo> wrapper = new LambdaEsQueryWrapper<>();
        wrapper.eq(StringUtil.isNotBlank(keywords), SystemLogPo::getMessage, keywords);
        EsPageInfo<SystemLogPo> pageInfo = systemLogDao.pageQuery(wrapper, pageNum, pageSize);
        PageDto<SystemLog> pageDto = new PageDto<>();
        pageDto.setTotal(pageInfo.getTotal());
        pageDto.setData(pageInfo.getList().stream().map(systemLogPo -> {
            SystemLog systemLogDto = new SystemLog();
            BeanUtils.copyProperties(systemLogPo, systemLogDto);
            return systemLogDto;
        }).collect(Collectors.toList()));
        return pageDto;
    }
}
