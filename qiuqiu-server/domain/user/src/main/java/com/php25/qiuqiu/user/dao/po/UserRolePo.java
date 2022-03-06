package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户与角色关系
 *
 * @author penghuiping
 * @date 2022/2/12 14:00
 */
@Setter
@Getter
@TableName("t_user_role")
public class UserRolePo {

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;
}

