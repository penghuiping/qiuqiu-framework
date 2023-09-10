package com.php25.qiuqiu.job.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.php25.common.db.DataPermission;
import com.php25.qiuqiu.job.dao.po.JobModelPo;
import org.apache.ibatis.annotations.Param;

/**
 * @author penghuiping
 * @date 2022/3/6 10:42
 */
public interface JobModelDao extends BaseMapper<JobModelPo> {

    @DataPermission
    @Override
    <P extends IPage<JobModelPo>> P selectPage(P page, @Param(Constants.WRAPPER) Wrapper<JobModelPo> queryWrapper);
}
