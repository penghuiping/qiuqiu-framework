package com.php25.qiuqiu.admin.vo.out.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.php25.qiuqiu.admin.vo.out.ResourcePermissionVo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/2/4 09:43
 */
@Setter
@Getter
public class UserVo {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色名列表
     */
    private List<String> roles;

    /**
     * 角色id
     */
    private List<Long> roleIds;

    /**
     * 资源名列表
     */
    private List<ResourcePermissionVo> resourcePermissions;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 组id
     */
    private Long groupId;

    /**
     * 用户信息创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 用户信息最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedTime;

    /**
     * 用户账号是否可用 true:可用 false:不可用
     */
    private Boolean enable;
}
