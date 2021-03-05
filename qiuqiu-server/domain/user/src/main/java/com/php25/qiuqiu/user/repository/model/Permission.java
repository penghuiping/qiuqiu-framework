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

/**
 * 权限
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
@Table("t_permission")
public class Permission implements Persistable<Long> {

    /**
     * id,自增
     */
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名
     */
    @Column
    private String name;

    /**
     * 权限描述
     */
    @Column
    private String description;

    /**
     * 此权限对应的后台接口地址
     */
    @Column
    private String uri;

    /**
     * 是否有效 0:无效 1:有效
     */
    @Column
    private Boolean enable;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
