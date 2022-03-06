package com.php25.qiuqiu.user.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.php25.qiuqiu.user.dao.po.ResourcePermissionPo;

/**
 * @author penghuiping
 * @date 2022/2/12 14:12
 */
public interface ResourcePermissionDao extends BaseMapper<ResourcePermissionPo> {

    int insertBatch(@Param("resourcePermissionPoCollection") Collection<ResourcePermissionPo> resourcePermissionPoCollection);
}
