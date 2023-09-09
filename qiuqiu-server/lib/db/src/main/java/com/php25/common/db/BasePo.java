package com.php25.common.db;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @author penghuiping
 * @date 2023/9/9 18:56
 */
public abstract class BasePo {

    /**
     * 创建人
     */
    @TableField(Constants.CREATE_USER)
    private String createUser;

    /**
     * 更新人
     */
    @TableField(Constants.UPDATE_USER)
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(Constants.CREATE_TIME)
    private Date createTime;

    /**
     * 最后修改时间
     */
    @TableField(Constants.UPDATE_TIME)
    private Date updateTime;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
