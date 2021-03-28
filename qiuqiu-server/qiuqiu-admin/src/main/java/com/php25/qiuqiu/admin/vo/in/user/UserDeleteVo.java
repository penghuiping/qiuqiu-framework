package com.php25.qiuqiu.admin.vo.in.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/6 10:14
 */
@Setter
@Getter
public class UserDeleteVo {

    /**
     * 用户id列表
     */
    @Size(min = 1)
    private List<Long> userIds;
}
