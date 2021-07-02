package com.php25.qiuqiu.user.entity;

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
 * 组织机构部门
 *
 * @author penghuiping
 * @date 2021/3/2 08:54
 */
@Setter
@Getter
@Table("t_group")
public class Group implements Persistable<Long> {

    /**
     * id,自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * 组名
     */
    @Column
    private String name;

    /**
     * 描述
     */
    @Column
    private String description;

    /**
     * 父节点id
     */
    @Column("parent_id")
    private Long parentId;

    /**
     * 是否有效 0:无效 1:有效
     */
    @Column
    private Boolean enable;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
