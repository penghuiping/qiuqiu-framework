package com.php25.qiuqiu.user.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author penghuiping
 * @date 2021/2/3 11:30
 */
@Getter
@Setter
public class User {

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
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifiedTime;


    private Long groupId;

    /**
     * 是否可用 0:不可用 1:可用
     */
    private Boolean enable;


    /**
     * 数据访问层级
     */
    private String dataAccessLevel;
}
