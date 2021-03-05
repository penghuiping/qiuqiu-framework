package com.php25.qiuqiu.user.repository.model;

import com.php25.common.db.core.GenerationType;
import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.GeneratedValue;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author penghuiping
 * @date 2021/2/3 11:30
 */
@Getter
@Setter
@Table("t_user")
public class User implements Persistable<Long> {

    /**
     * id,自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * 用户昵称
     */
    @Column
    private String nickname;

    /**
     * 用户名全局唯一不可重复
     */
    @Column
    private String username;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 创建时间
     */
    @Column("create_time")
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @Column("last_modified_time")
    private LocalDateTime lastModifiedTime;

    /**
     * 组织机构部门
     */
    @Column("group_id")
    private Long groupId;

    /**
     * 是否可用 0:不可用 1:可用
     */
    @Column
    private Boolean enable;

    /**
     * 用于判断是新增还是更新,true:新增 false:更新
     */
    @Transient
    private Boolean isNew;


    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
