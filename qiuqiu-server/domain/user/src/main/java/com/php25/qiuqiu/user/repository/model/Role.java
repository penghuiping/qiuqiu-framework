package com.php25.qiuqiu.user.repository.model;

import com.php25.common.db.core.GenerationType;
import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

/**
 * 角色
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
public class Role implements Persistable<Long> {

    /**
     * id,自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * 角色名
     */
    @Column
    private String name;

    /**
     * 角色描述
     */
    @Column
    private String description;

    /**
     * 是否可用 0:不可用 1:可用
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
