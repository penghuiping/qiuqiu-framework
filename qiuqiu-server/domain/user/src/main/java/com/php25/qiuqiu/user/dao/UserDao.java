package com.php25.qiuqiu.user.dao;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.php25.qiuqiu.user.dao.po.UserPo;

/**
 * @author penghuiping
 * @date 2022/2/12 13:50
 */
public interface UserDao extends BaseMapper<UserPo> {
    int insertBatch(@Param("userPoCollection") Collection<UserPo> userPoCollection);
}
