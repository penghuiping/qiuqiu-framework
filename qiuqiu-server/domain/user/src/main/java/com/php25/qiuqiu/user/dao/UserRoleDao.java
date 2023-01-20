package com.php25.qiuqiu.user.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.php25.qiuqiu.user.dao.po.UserRolePo;

import java.util.List;

/**
 * @author penghuiping
 * @date 2022/2/12 14:10
 */
public interface UserRoleDao extends BaseMapperPlus<UserRolePo> {
    int insertBatch(@Param("userRolePoCollection") Collection<UserRolePo> userRolePoCollection);
}
