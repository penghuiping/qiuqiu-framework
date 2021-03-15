package com.php25.qiuqiu.job.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.mess.SpringContextHolder;
import com.php25.common.core.util.RandomUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.common.mq.Message;
import com.php25.common.mq.MessageQueueManager;
import com.php25.common.timer.CronExpression;
import com.php25.common.timer.Job;
import com.php25.common.timer.Timer;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobLogCreateDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import com.php25.qiuqiu.job.model.JobLog;
import com.php25.qiuqiu.job.model.JobModel;
import com.php25.qiuqiu.job.repository.JobLogRepository;
import com.php25.qiuqiu.job.repository.JobModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/13 18:15
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService, InitializingBean {

    private final JobModelRepository jobModelRepository;

    private final JobLogRepository jobLogRepository;

    private final Timer timer;

    private final MessageQueueManager messageQueueManager;

    @Value("${server.id}")
    private String serverId;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageQueueManager.subscribe("timer_job_enabled", message -> {
            String jobId = (String) message.getBody();
            this.timer.stop(jobId);

            Optional<JobModel> jobModelOptional = this.jobModelRepository.findById(jobId);
            if (!jobModelOptional.isPresent()) {
                return;
            }

            JobModel jobModel = jobModelOptional.get();
            if (!jobModel.getEnable()) {
                return;
            }

            String className = jobModel.getClassName();
            Runnable task = null;
            try {
                Class<?> cls = Class.forName(className);
                task = (Runnable) SpringContextHolder.getBean0(cls);
            } catch (Exception e) {
                log.error("定时任务对应的执行代码类加载出错", e);
                return;
            }
            try {
                Date date = new CronExpression(jobModel.getCron()).getNextValidTimeAfter(new Date());
                if (null == date) {
                    return;
                }
                Job job = new Job(jobModel.getId(), jobModel.getCron(), task);
                this.timer.add(job);
            } catch (ParseException e) {
                log.error("定时任务cron表达式出错", e);
            }
        });

        messageQueueManager.subscribe("timer_job_disabled", serverId, message -> {
            String jobId = (String) message.getBody();
            this.timer.stop(jobId);
        });
    }

    @Override
    public DataGridPageDto<JobDto> page(String name, Integer pageNum, Integer pageSize) {
        SearchParamBuilder searchParamBuilder = new SearchParamBuilder();
        if (!StringUtil.isBlank(name)) {
            searchParamBuilder.append(SearchParam.of("name", Operator.EQ, name));
        }
        PageRequest request = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<JobModel> page = jobModelRepository.findAll(searchParamBuilder, request);

        List<JobDto> jobDtoList = page.get().map(jobModel -> {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(jobModel, jobDto);
            return jobDto;
        }).collect(Collectors.toList());
        DataGridPageDto<JobDto> dataGrid = new DataGridPageDto<>();
        dataGrid.setData(jobDtoList);
        dataGrid.setRecordsTotal(page.getTotalElements());
        return dataGrid;
    }

    @Override
    public Boolean create(JobCreateDto job) {
        JobModel jobModel = new JobModel();
        BeanUtils.copyProperties(job, jobModel);
        jobModel.setId(RandomUtil.randomUUID());
        jobModel.setEnable(true);
        jobModel.setIsNew(true);
        jobModelRepository.save(jobModel);
        return true;
    }

    @Override
    public Boolean update(JobUpdateDto job) {
        JobModel jobModel = new JobModel();
        BeanUtils.copyProperties(job, jobModel);
        jobModel.setIsNew(false);
        jobModelRepository.save(jobModel);
        return true;
    }

    @Override
    public Boolean delete(String jobId) {
        jobModelRepository.deleteById(jobId);
        return true;
    }

    @Override
    public DataGridPageDto<JobLogDto> pageJobLog(String jobId, Integer pageNum, Integer pageSize) {
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if (!StringUtil.isBlank(jobId)) {
            builder.append(SearchParam.of("jobId", Operator.EQ, jobId));
        }
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<JobLog> page = jobLogRepository.findAll(builder, pageRequest);
        DataGridPageDto<JobLogDto> dataGrid = new DataGridPageDto<>();
        List<JobLogDto> jobLogDtoList = page.get().map(jobLog -> {
            JobLogDto jobLogDto = new JobLogDto();
            BeanUtils.copyProperties(jobLog, jobLogDto);
            return jobLogDto;
        }).collect(Collectors.toList());
        dataGrid.setData(jobLogDtoList);
        dataGrid.setRecordsTotal(page.getTotalElements());
        return dataGrid;
    }

    @Override
    public Boolean createJobLog(JobLogCreateDto jobLog) {
        JobLog jobLog0 = new JobLog();
        BeanUtils.copyProperties(jobLog, jobLog0);
        jobLog0.setIsNew(true);
        jobLogRepository.save(jobLog0);
        return true;
    }

    @Override
    public Boolean refresh(String jobId) {
        Optional<JobModel> jobModelOptional = this.jobModelRepository.findById(jobId);
        if (!jobModelOptional.isPresent()) {
            return false;
        }
        JobModel jobModel = jobModelOptional.get();
        if (jobModel.getEnable()) {
            Message message = new Message(jobId, "timer_job_enabled", jobId);
            messageQueueManager.send("timer_job_enabled", message);
        } else {
            Message message = new Message(jobId, "timer_job_disabled", this.serverId, jobId);
            messageQueueManager.send("timer_job_disabled", message);
        }
        return true;
    }
}
