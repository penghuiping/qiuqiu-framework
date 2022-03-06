package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 权限
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
@TableName("t_permission")
public class PermissionPo  {

    /**
     * 权限名
     */
    @TableId(type = IdType.INPUT)
    private String name;

    /**
     * 权限描述
     */
    @TableField
    private String description;

    /**
     * 是否有效 0:无效 1:有效
     */
    @TableField
    private Boolean enable;
}
