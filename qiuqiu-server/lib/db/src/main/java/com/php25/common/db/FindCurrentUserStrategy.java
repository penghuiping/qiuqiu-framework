package com.php25.common.db;

import com.php25.common.core.dto.CurrentUser;

/**
 * @author penghuiping
 * @date 2023/9/9 21:59
 */
public interface FindCurrentUserStrategy {

    /**
     *
     * @return 当前登录用户
     */
    CurrentUser find();
}
