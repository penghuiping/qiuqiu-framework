package com.php25.qiuqiu.monitor.model;

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
 * @date 2021/3/11 14:52
 */
@Setter
@Getter
@Table("t_audit_log")
public class AuditLog implements Persistable<Long> {

    /**
     * 日志id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 系统用户名
     */
    @Column
    private String username;

    /**
     * 系统接口地址
     */
    @Column
    private String uri;

    /**
     * 接口入参
     */
    @Column
    private String params;

    /**
     * 创建时间
     */
    @Column("create_time")
    private LocalDateTime createTime;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
