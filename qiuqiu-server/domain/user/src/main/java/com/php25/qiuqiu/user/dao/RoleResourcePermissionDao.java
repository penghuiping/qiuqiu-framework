package com.php25.qiuqiu.user.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.php25.qiuqiu.user.dao.po.RoleResourcePermissionPo;

/**
 * @author penghuiping
 * @date 2022/2/12 14:11
 */
public interface RoleResourcePermissionDao extends BaseMapper<RoleResourcePermissionPo> {

    int insertBatch(@Param("roleResourcePermissionPoCollection") Collection<RoleResourcePermissionPo> roleResourcePermissionPoCollection);
}
