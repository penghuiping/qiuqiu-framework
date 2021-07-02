package com.php25.qiuqiu.user.entity;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

/**
 * @author penghuiping
 * @date 2021/3/25 16:16
 */
@Setter
@Getter
@Table("t_resource")
public class Resource implements Persistable<String> {

    /**
     * 资源名
     */
    @Id
    @Column
    private String name;

    /**
     * 资源描述
     */
    @Column
    private String description;

    /**
     * 是否有效 0:无效 1:有效
     */
    @Column
    private Boolean enable;

    @Transient
    private Boolean isNew;

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
