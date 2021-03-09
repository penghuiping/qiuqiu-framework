package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/9 15:30
 */
@Setter
@Getter
public class RoleDeleteVo {
    /**
     * 需要删除角色id列表
     */
    List<Long> roleIds;
}
