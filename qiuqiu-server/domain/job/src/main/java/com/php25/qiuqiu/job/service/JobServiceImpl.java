package com.php25.qiuqiu.job.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.util.JsonUtil;
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
import com.php25.qiuqiu.job.dto.BaseRunnable;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import com.php25.qiuqiu.job.dto.JobLogCreateDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import com.php25.qiuqiu.job.model.JobExecution;
import com.php25.qiuqiu.job.model.JobLog;
import com.php25.qiuqiu.job.model.JobModel;
import com.php25.qiuqiu.job.repository.JobExecutionRepository;
import com.php25.qiuqiu.job.repository.JobLogRepository;
import com.php25.qiuqiu.job.repository.JobModelRepository;
import com.php25.qiuqiu.user.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
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
public class JobServiceImpl implements JobService, InitializingBean, DisposableBean {

    private final JobModelRepository jobModelRepository;

    private final JobLogRepository jobLogRepository;

    private final JobExecutionRepository jobExecutionRepository;

    private final GroupService groupService;

    private final Timer timer;

    private final MessageQueueManager messageQueueManager;

    @Value("${server.id}")
    private String serverId;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.subscribeRefreshJobDisabled();
        this.subscribeRefreshJobEnabled();
    }

    @Override
    public void destroy() throws Exception {
        List<JobExecution> jobExecutions = (List<JobExecution>) this.jobExecutionRepository.findAll();
        List<JobExecution> jobExecutions1 = jobExecutions.stream().map(jobExecution -> {
            jobExecution.setStatus(0);
            jobExecution.setIsNew(false);
            return jobExecution;
        }).collect(Collectors.toList());
        this.jobExecutionRepository.saveAll(jobExecutions1);
        this.messageQueueManager.delete("timer_job_disabled",serverId);
        this.messageQueueManager.delete("timer_job_enabled",serverId);
    }

    @Override
    public DataGridPageDto<JobDto> page(String username, String name, Integer pageNum, Integer pageSize) {
        List<Long> groupIds = groupService.findGroupsId(username);
        SearchParamBuilder searchParamBuilder = new SearchParamBuilder();
        if (!StringUtil.isBlank(name)) {
            searchParamBuilder.append(SearchParam.of("name", Operator.EQ, name));
        }
        searchParamBuilder.append(SearchParam.of("groupId", Operator.IN, groupIds));
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
    public List<JobDto> findAll(String username) {
        List<Long> groupIds = groupService.findGroupsId(username);
        SearchParamBuilder searchParamBuilder = new SearchParamBuilder();
        searchParamBuilder.append(SearchParam.of("groupId", Operator.IN, groupIds));
        List<JobModel> list = jobModelRepository.findAll(searchParamBuilder);
        return list.stream().map(jobModel -> {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(jobModel, jobDto);
            return jobDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Boolean create(String username, JobCreateDto job) {
        Long groupId = groupService.findGroupId(username);
        JobModel jobModel = new JobModel();
        BeanUtils.copyProperties(job, jobModel);
        jobModel.setGroupId(groupId);
        jobModel.setId(RandomUtil.randomUUID());
        jobModel.setIsNew(true);
        jobModelRepository.save(jobModel);
        return true;
    }

    @Override
    public Boolean update(String username, JobUpdateDto job) {
        JobModel jobModel = new JobModel();
        BeanUtils.copyProperties(job, jobModel);
        jobModel.setIsNew(false);
        jobModelRepository.save(jobModel);
        return true;
    }

    @Override
    public Boolean delete(String username, String jobId) {
        List<Long> groupsId = groupService.findGroupsId(username);
        Optional<JobModel> jobModelOptional = jobModelRepository.findById(jobId);
        if (jobModelOptional.isPresent()) {
            JobModel jobModel = jobModelOptional.get();
            if (groupsId.contains(jobModel.getGroupId())) {
                jobModelRepository.deleteById(jobId);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public DataGridPageDto<JobLogDto> pageJobLog(String username, String jobName, Integer pageNum, Integer pageSize) {
        List<Long> groupsId = groupService.findGroupsId(username);
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if (!StringUtil.isBlank(jobName)) {
            builder.append(SearchParam.of("jobName", Operator.EQ, jobName));
        }
        builder.append(SearchParam.of("groupId", Operator.IN, groupsId));
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
        String jobId = jobLog.getJobId();
        Optional<JobModel> jobModelOptional = jobModelRepository.findById(jobId);
        if (!jobModelOptional.isPresent()) {
            return false;
        }
        JobModel jobModel = jobModelOptional.get();
        JobLog jobLog0 = new JobLog();
        BeanUtils.copyProperties(jobLog, jobLog0);
        jobLog0.setGroupId(jobModel.getGroupId());
        jobLog0.setIsNew(true);
        jobLogRepository.save(jobLog0);
        return true;
    }

    @Override
    public Boolean createJobExecution(String username, JobExecutionCreateDto jobExecution) {
        Optional<JobModel> jobModelOptional = jobModelRepository.findById(jobExecution.getJobId());
        if (!jobModelOptional.isPresent()) {
            return false;
        }
        Long groupId = groupService.findGroupId(username);
        JobExecution jobExecution0 = new JobExecution();
        BeanUtils.copyProperties(jobExecution, jobExecution0);
        jobExecution0.setGroupId(groupId);
        jobExecution0.setEnable(true);
        jobExecution0.setStatus(0);
        jobExecution0.setJobName(jobModelOptional.get().getName());
        jobExecution0.setId(RandomUtil.randomUUID());
        jobExecution0.setIsNew(true);
        jobExecutionRepository.save(jobExecution0);
        return true;
    }

    @Override
    public Boolean updateJobExecution(String username, JobExecutionUpdateDto jobExecution) {
        Optional<JobExecution> jobExecutionOptional = jobExecutionRepository.findById(jobExecution.getId());
        if (!jobExecutionOptional.isPresent()) {
            return false;
        }
        JobExecution jobExecution1 = jobExecutionOptional.get();
        Long groupId = jobExecution1.getGroupId();
        List<Long> groupIds = groupService.findGroupsId(username);
        if (groupIds.contains(groupId)) {
            //可以更新
            JobExecution jobExecution0 = new JobExecution();
            BeanUtils.copyProperties(jobExecution, jobExecution0);
            jobExecution0.setIsNew(false);
            jobExecutionRepository.save(jobExecution0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteJobExecution(String username, String id) {
        List<Long> groupsId = groupService.findGroupsId(username);
        Optional<JobExecution> jobExecutionOptional = jobExecutionRepository.findById(id);
        if (!jobExecutionOptional.isPresent()) {
            return false;
        }
        JobExecution jobExecution = jobExecutionOptional.get();
        Long groupId = jobExecution.getGroupId();
        if (groupsId.contains(groupId)) {
            jobExecutionRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public DataGridPageDto<JobExecutionDto> pageJobExecution(String username, String jobName, Integer pageNum, Integer pageSize) {
        List<Long> groupIds = groupService.findGroupsId(username);
        SearchParamBuilder builder = SearchParamBuilder.builder();
        if (StringUtil.isNotBlank(jobName)) {
            builder.append(SearchParam.of("jobName", Operator.EQ, jobName));
        }
        builder.append(SearchParam.of("groupId", Operator.IN, groupIds));
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<JobExecution> page = this.jobExecutionRepository.findAll(builder, pageRequest);
        DataGridPageDto<JobExecutionDto> dataGrid = new DataGridPageDto<>();
        dataGrid.setRecordsTotal(page.getTotalElements());
        dataGrid.setData(page.get().map(jobExecution -> {
            JobExecutionDto jobExecutionDto = new JobExecutionDto();
            BeanUtils.copyProperties(jobExecution, jobExecutionDto);
            return jobExecutionDto;
        }).collect(Collectors.toList()));
        return dataGrid;
    }

    @Override
    public Boolean refresh(String username, String executionId) {
        Optional<JobExecution> jobExecutionOptional = this.jobExecutionRepository.findById(executionId);
        if (!jobExecutionOptional.isPresent()) {
            return false;
        }
        JobExecution jobExecution = jobExecutionOptional.get();
        if (jobExecution.getEnable()) {
            Message message = new Message(RandomUtil.randomUUID(), executionId);
            messageQueueManager.send("timer_job_enabled", message);
        } else {
            Message message = new Message(RandomUtil.randomUUID(), executionId);
            messageQueueManager.send("timer_job_disabled", message);
        }
        return true;
    }

    @Override
    public Boolean refreshAll(String username) {
        List<JobExecution> jobExecutions = (List<JobExecution>) this.jobExecutionRepository.findAll();
        if (!jobExecutions.isEmpty()) {
            for (JobExecution jobExecution : jobExecutions) {
                this.refresh(username, jobExecution.getId());
            }
        }
        return true;
    }

    private void loadExecution(String executionId) {
        Optional<JobExecution> jobExecutionOptional = this.jobExecutionRepository.findById(executionId);
        if (!jobExecutionOptional.isPresent()) {
            return;
        }

        JobExecution jobExecution = jobExecutionOptional.get();
        if (!jobExecution.getEnable()) {
            return;
        }

        String jobId = jobExecution.getJobId();
        Optional<JobModel> jobModelOptional = this.jobModelRepository.findById(jobId);
        if (!jobModelOptional.isPresent()) {
            return;
        }
        JobModel jobModel = jobModelOptional.get();
        String className = jobModel.getClassName();
        Runnable task = null;
        try {
            Class<?> cls = Class.forName(className);
            task = (BaseRunnable) cls.getDeclaredConstructor(String.class, String.class)
                    .newInstance(jobModel.getId(), jobModel.getName());
        } catch (Exception e) {
            log.error("定时任务对应的执行代码类加载出错", e);
            return;
        }
        try {
            Date date = new CronExpression(jobExecution.getCron()).getNextValidTimeAfter(new Date());
            if (null == date) {
                return;
            }
            Job job = new Job(executionId, jobExecution.getCron(), task);
            this.timer.add(job,true);
            JobExecution jobExecution0 = new JobExecution();
            jobExecution0.setId(jobExecution.getId());
            jobExecution0.setStatus(1);
            jobExecution0.setIsNew(false);
            jobExecutionRepository.save(jobExecution0);
        } catch (ParseException e) {
            log.error("定时任务cron表达式出错", e);
        }
    }

    private void subscribeRefreshJobEnabled() {
        messageQueueManager.subscribe("timer_job_enabled",serverId, message -> {
            log.info("timer_job_enabled:{}", JsonUtil.toJson(message));
            String executionId = (String) message.getBody();
            this.timer.stop(executionId);
            loadExecution(executionId);
        });
    }

    private void subscribeRefreshJobDisabled() {
        messageQueueManager.subscribe("timer_job_disabled", serverId, message -> {
            log.info("timer_job_disabled:{}",JsonUtil.toJson(message));
            String executionId = (String) message.getBody();
            this.timer.stop(executionId);
            Optional<JobExecution> jobExecutionOptional = jobExecutionRepository.findById(executionId);
            if (jobExecutionOptional.isPresent()) {
                JobExecution jobExecution = jobExecutionOptional.get();
                JobExecution jobExecution0 = new JobExecution();
                jobExecution0.setId(jobExecution.getId());
                jobExecution0.setStatus(0);
                jobExecution0.setIsNew(false);
                jobExecutionRepository.save(jobExecution0);
            }
        });
    }
}
