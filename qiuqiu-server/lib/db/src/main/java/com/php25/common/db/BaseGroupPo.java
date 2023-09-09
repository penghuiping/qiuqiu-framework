package com.php25.common.db;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @author penghuiping
 * @date 2023/9/9 19:01
 */
public abstract class BaseGroupPo extends BasePo {
    /**
     * 组id
     */
    @TableField(Constants.GROUP_ID)
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
