package com.php25.qiuqiu.monitor.entity;

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
 * @author penghuiping
 * @date 2021/3/11 16:16
 */
@Setter
@Getter
@Table("t_dict")
public class Dict implements Persistable<Long> {

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 键
     */
    @Column("key0")
    private String key;

    /**
     * 值
     */
    @Column
    private String value;

    /**
     * 描述
     */
    @Column
    private String description;

    /**
     * true: 有效,false: 无效
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
