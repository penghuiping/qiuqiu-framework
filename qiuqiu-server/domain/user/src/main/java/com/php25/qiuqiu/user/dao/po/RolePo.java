package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * /**
 * 角色
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
@TableName("t_role")
public class RolePo {

    /**
     * id,自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名
     */
    @TableField
    private String name;

    /**
     * 角色描述
     */
    @TableField
    private String description;

    /**
     * 是否可用 0:不可用 1:可用
     */
    @TableField
    private Boolean enable;
}

