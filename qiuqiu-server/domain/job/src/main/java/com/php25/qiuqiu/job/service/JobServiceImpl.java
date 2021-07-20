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
import com.php25.qiuqiu.job.dto.JobExecutionStatisticReqDto;
import com.php25.qiuqiu.job.dto.JobExecutionStatisticResDto;
import com.php25.qiuqiu.job.dto.JobExecutionUpdateDto;
import com.php25.qiuqiu.job.dto.JobLogCreateDto;
import com.php25.qiuqiu.job.dto.JobLogDto;
import com.php25.qiuqiu.job.dto.JobUpdateDto;
import com.php25.qiuqiu.job.entity.JobExecution;
import com.php25.qiuqiu.job.entity.JobLog;
import com.php25.qiuqiu.job.entity.JobModel;
import com.php25.qiuqiu.job.mapper.JobDtoMapper;
import com.php25.qiuqiu.job.repository.JobExecutionRepository;
import com.php25.qiuqiu.job.repository.JobLogRepository;
import com.php25.qiuqiu.job.repository.JobModelRepository;
import com.php25.qiuqiu.user.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    private final JobDtoMapper jobDtoMapper;

    @Value("${server.id}")
    private String serverId;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.subscribeRefreshJobDisabled();
        this.subscribeRefreshJobEnabled();

        this.statisticLoadedJobExecutionSubscribe();
        this.mergeStatisticLoadedJobExecutionSubscribe();
    }

    @Override
    public void destroy() throws Exception {

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

        List<JobDto> jobDtoList = page.get().map(jobDtoMapper::toDto).collect(Collectors.toList());
        DataGridPageDto<JobDto> dataGrid = new DataGridPageDto<>();
        dataGrid.setData(jobDtoList);
        dataGrid.setRecordsTotal(page.getTotalElements());
        return dataGrid;
    }

    @Override
    public List<JobDto> findAll(String username) {
        SearchParamBuilder searchParamBuilder = groupService.getDataAccessScope(username);
        List<JobModel> list = jobModelRepository.findAll(searchParamBuilder);
        return list.stream().map(jobDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Boolean create(String username, JobCreateDto job) {
        Long groupId = groupService.findGroupId(username);
        JobModel jobModel = jobDtoMapper.toEntity(job);
        jobModel.setGroupId(groupId);
        jobModel.setId(RandomUtil.randomUUID());
        jobModel.setIsNew(true);
        jobModelRepository.save(jobModel);
        return true;
    }

    @Override
    public Boolean update(String username, JobUpdateDto job) {
        JobModel jobModel = jobDtoMapper.toEntity(job);
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
        if (StringUtil.isNotBlank(jobName)) {
            builder.append(SearchParam.of("jobName", Operator.EQ, jobName));
        }
        builder.append(SearchParam.of("groupId", Operator.IN, groupsId));
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("id")));
        Page<JobLog> page = jobLogRepository.findAll(builder, pageRequest);
        DataGridPageDto<JobLogDto> dataGrid = new DataGridPageDto<>();
        List<JobLogDto> jobLogDtoList = page.get().map(jobDtoMapper::toDto0).collect(Collectors.toList());
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
        JobLog jobLog0 = jobDtoMapper.toEntity0(jobLog);
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
        JobExecution jobExecution0 = jobDtoMapper.toEntity1(jobExecution);
        jobExecution0.setGroupId(groupId);
        jobExecution0.setEnable(true);
        jobExecution0.setTimerLoadedNumber(0);
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
            JobExecution jobExecution0 = jobDtoMapper.toEntity1(jobExecution);
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
        dataGrid.setData(page.get().map(jobDtoMapper::toDto1).collect(Collectors.toList()));
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

    private void statisticLoadedJobExecutionSubscribe() {
        messageQueueManager.subscribe("statistic_loaded_job_execution", serverId, true, message -> {
            String tmp = JsonUtil.toJson(message.getBody());
            log.info("JobExecutionStatisticReqDto为:{}", tmp);
            JobExecutionStatisticReqDto reqDto = JsonUtil.fromJson(tmp, JobExecutionStatisticReqDto.class);
            Set<String> executionIds = timer.getAllLoadedExecutionIds();
            Map<String,Integer> res = new HashMap<>();
            for(String executionId: executionIds) {
                res.put(executionId,1);
            }
            JobExecutionStatisticResDto resDto = new JobExecutionStatisticResDto();
            resDto.setEntries(res);
            Message message0 = new Message(RandomUtil.randomUUID(), resDto);
            messageQueueManager.send(reqDto.getQueue(), reqDto.getGroup(), message0);
        });
    }

    private void mergeStatisticLoadedJobExecutionSubscribe() {
        messageQueueManager.subscribe("merge_statistic_loaded_job_execution", serverId, true, message -> {
            JobExecutionStatisticResDto res = JsonUtil.fromJson(JsonUtil.toJson(message.getBody()),JobExecutionStatisticResDto.class );
            res.getEntries().forEach((key, value) -> {
                Optional<JobExecution> jobExecutionOptional = jobExecutionRepository.findById(key);
                if (jobExecutionOptional.isPresent()) {
                    JobExecution jobExecution = jobExecutionOptional.get();
                    JobExecution jobExecution0 = new JobExecution();
                    jobExecution0.setId(jobExecution.getId());
                    jobExecution0.setTimerLoadedNumber(jobExecution.getTimerLoadedNumber() + value);
                    jobExecution0.setIsNew(false);
                    jobExecutionRepository.save(jobExecution0);
                }
            });
        });
    }

    @Override
    public void statisticLoadedJobExecutionInfo() {
        JobExecutionStatisticReqDto reqDto = new JobExecutionStatisticReqDto();
        reqDto.setGroup(serverId);
        reqDto.setQueue("merge_statistic_loaded_job_execution");
        Message message = new Message(RandomUtil.randomUUID(), reqDto);
        jobExecutionRepository.resetTimerLoadedNumber();
        messageQueueManager.send("statistic_loaded_job_execution", message);
    }

    private void loadExecution(String executionId) {
        if (this.timer.getAllLoadedExecutionIds().contains(executionId)) {
            return;
        }

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
            task = (BaseRunnable) cls.getDeclaredConstructor(String.class, String.class,String.class)
                    .newInstance(jobModel.getId(), jobModel.getName(),executionId);
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
            this.timer.add(job, true);
        } catch (ParseException e) {
            log.error("定时任务cron表达式出错", e);
        }
    }

    @Override
    public JobExecutionDto findOne(String jobExecutionId) {
        Optional<JobExecution> jobExecutionOptional = this.jobExecutionRepository.findById(jobExecutionId);
        return jobExecutionOptional.map(jobDtoMapper::toDto1).orElse(null);
    }

    private void subscribeRefreshJobEnabled() {
        messageQueueManager.subscribe("timer_job_enabled", serverId, true, message -> {
            log.info("timer_job_enabled:{}", JsonUtil.toJson(message));
            String executionId = (String) message.getBody();
            loadExecution(executionId);
        });
    }

    private void subscribeRefreshJobDisabled() {
        messageQueueManager.subscribe("timer_job_disabled", serverId, true, message -> {
            log.info("timer_job_disabled:{}", JsonUtil.toJson(message));
            String executionId = (String) message.getBody();
            this.timer.stop(executionId);
        });
    }
}
