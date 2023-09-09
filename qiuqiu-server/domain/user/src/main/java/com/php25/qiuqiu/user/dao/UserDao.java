package com.php25.qiuqiu.user.dao;

import com.php25.common.db.BaseMapperPlus;
import com.php25.qiuqiu.user.dao.po.UserPo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author penghuiping
 * @date 2022/2/12 13:50
 */
public interface UserDao extends BaseMapperPlus<UserPo> {
    int insertBatch(@Param("userPoCollection") Collection<UserPo> userPoCollection);
}
