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
 * @author penghuiping
 * @date 2021/2/3 11:30
 */
@Getter
@Setter
@Table("t_user")
public class User implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nickname;

    @Column
    private String username;

    @Column
    private String password;

    @Column("create_time")
    private LocalDateTime createTime;

    @Column("last_modified_time")
    private LocalDateTime lastModifiedTime;

    @Column
    private Integer enable;

    @Transient
    private Boolean isNew;


    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
