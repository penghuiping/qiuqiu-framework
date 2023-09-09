package com.php25.qiuqiu.user.dao;
import com.php25.common.db.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

import com.php25.qiuqiu.user.dao.po.UserRolePo;

/**
 * @author penghuiping
 * @date 2022/2/12 14:10
 */
public interface UserRoleDao extends BaseMapperPlus<UserRolePo> {
    int insertBatch(@Param("userRolePoCollection") Collection<UserRolePo> userRolePoCollection);
}
