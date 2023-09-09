package com.php25.common.core.dto;

/**
 * @author penghuiping
 * @date 2022/11/6 15:22
 */
public class CurrentUserDto extends CurrentUser{

    /**
     * 用户组编码
     */
    private String groupCode;

    /**
     * 用户组名字
     */
    private String groupName;


    /**
     * 数据权限范围
     */
    private DataAccessLevel dataAccessLevel;


    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public DataAccessLevel getDataAccessLevel() {
        return dataAccessLevel;
    }

    public void setDataAccessLevel(DataAccessLevel dataAccessLevel) {
        this.dataAccessLevel = dataAccessLevel;
    }
}
