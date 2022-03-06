package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/25 16:16
 */
@Setter
@Getter
@TableName("t_resource")
public class ResourcePo {

    /**
     * 资源名
     */
    @TableId(type = IdType.INPUT)
    @TableField
    private String name;

    /**
     * 资源描述
     */
    @TableField
    private String description;

    /**
     * 是否有效 0:无效 1:有效
     */
    @TableField
    private Boolean enable;
}
