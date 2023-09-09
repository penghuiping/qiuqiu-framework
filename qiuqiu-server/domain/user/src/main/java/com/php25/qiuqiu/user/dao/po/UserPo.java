package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.php25.common.db.BaseGroupPo;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2022/2/12 13:51
 */
@Getter
@Setter
@TableName(value = "t_user")
public class UserPo extends BaseGroupPo {
    /**
     * id,自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户名全局唯一不可重复
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 是否可用 0:不可用 1:可用
     */
    @TableField
    private Boolean enable;


    /**
     * 数据访问层级
     */
    @TableField("data_access_level")
    private String dataAccessLevel;
}
