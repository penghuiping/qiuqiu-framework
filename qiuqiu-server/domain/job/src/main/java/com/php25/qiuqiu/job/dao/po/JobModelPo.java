package com.php25.qiuqiu.job.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/8/24 13:52
 */
@Getter
@Setter
@TableName("t_timer_job")
public class JobModelPo {

    /**
     * 任务id
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * job名字可以用于搜索
     */
    @TableField
    private String name;

    /**
     * job描述
     */
    @TableField
    private String description;

    /**
     * 任务对应的java执行代码
     */
    @TableField("class_name")
    private String className;

    /**
     * 用户组id
     */
    @TableField("group_id")
    private Long groupId;
}
