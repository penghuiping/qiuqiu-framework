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
 * @date 2021/3/15 20:23
 */
@Setter
@Getter
@Table("t_timer_job_execution")
public class JobExecution implements Persistable<String> {

    /**
     * 任务执行id
     */
    @Id
    private String id;

    /**
     * 任务的cron表达式
     */
    @Column
    private String cron;

    /**
     * 用户组id
     */
    @Column("group_id")
    private Long groupId;

    /**
     * 任务名
     */
    @Column("job_id")
    private String jobId;

    /**
     * 任务名字
     */
    @Column("job_name")
    private String jobName;

    /**
     * 0:无效 1:有效
     */
    @Column
    private Boolean enable;

    /**
     * 0: 没载入定时器
     * 1: 已载入定时器
     */
    @Column
    private Integer status;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
