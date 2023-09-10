package com.php25.qiuqiu.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.common.db.BaseMapperPlus;
import com.php25.common.db.DataPermission;
import com.php25.qiuqiu.user.dao.po.UserPo;
import com.php25.qiuqiu.user.dao.view.UserView;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author penghuiping
 * @date 2022/2/12 13:50
 */
public interface UserDao extends BaseMapperPlus<UserPo> {
    int insertBatch(@Param("userPoCollection") Collection<UserPo> userPoCollection);

    @DataPermission
    IPage<UserView> selectPageByUsername(IPage<UserPo> page,@Param("username")String username);
}
