package com.php25.qiuqiu.user.dao.view;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author penghuiping
 * @date 2023/9/10 14:25
 */
@Setter
@Getter
public class UserView {

    /**
     * id,自增
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名全局唯一不可重复
     */
    private String username;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    /**
     * 用户组id
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 用户组名
     */
    @TableField("group_name")
    private String groupName;


    /**
     * 是否可用 0:不可用 1:可用
     */
    private Boolean enable;


    /**
     * 数据访问层级
     */
    @TableField("data_access_level")
    private String dataAccessLevel;
}
