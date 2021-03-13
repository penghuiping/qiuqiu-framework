package com.php25.qiuqiu.job.model;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

/**
 * @author penghuiping
 * @date 2020/8/24 13:52
 */
@Table("t_timer_job")
@Getter
@Setter
public class JobModel implements Persistable<String> {

    /**
     * 任务id
     */
    @Id
    private String id;

    /**
     * job名字可以用于搜索
     */
    @Column
    private String name;

    /**
     * job描述
     */
    @Column
    private String description;

    /**
     * 任务对应的java执行代码
     */
    @Column("class_name")
    private String className;

    /**
     * 任务的cron表达式
     */
    @Column
    private String cron;

    /**
     * 0:无效 1:有效
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
