package com.php25.qiuqiu.job.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.php25.common.db.DataPermission;
import com.php25.qiuqiu.job.dao.po.JobLogPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author penghuiping
 * @date 2022/3/6 10:41
 */
public interface JobLogDao extends BaseMapper<JobLogPo> {

    @DataPermission
    @Override
    <P extends IPage<JobLogPo>> P selectPage(P page,@Param(Constants.WRAPPER) Wrapper<JobLogPo> queryWrapper);
}
