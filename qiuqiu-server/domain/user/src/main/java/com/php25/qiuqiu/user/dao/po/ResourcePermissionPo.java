package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 资源与权限关系
 *
 * @author penghuiping
 * @date 2021/3/2 09:15
 */
@Setter
@Getter
@TableName("t_resource_permission")
public class ResourcePermissionPo {

    /**
     * 资源id
     */
    @TableField("resource")
    private String resource;

    /**
     * 权限id
     */
    @TableField("permission")
    private String permission;
}
