package com.php25.qiuqiu.job.model;

import com.php25.common.db.core.GenerationType;
import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.GeneratedValue;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.Date;

/**
 * 任务执行日志
 *
 * @author penghuiping
 * @date 2021/3/15 13:56
 */
@Setter
@Getter
@Table("t_timer_job_log")
public class JobLog implements Persistable<Long> {
    /**
     * 日志自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务id
     */
    @Column("job_name")
    private String jobName;

    /**
     * 任务执行时间
     */
    @Column("execute_time")
    private Date executeTime;

    /**
     * 任务执行 0:失败 1:成功
     */
    @Column("result_code")
    private Integer resultCode;

    /**
     * 结果描述
     */
    @Column("result_message")
    private String resultMessage;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
