package com.php25.qiuqiu.user.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织机构部门
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
@TableName("t_group")
public class GroupPo  {

    /**
     * id,自增
     */
    @TableId
    private Long id;

    /**
     * 组名
     */
    @TableField
    private String name;

    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * 父节点id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 是否有效 0:无效 1:有效
     */
    @TableField
    private Boolean enable;
}
