package com.php25.qiuqiu.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.php25.common.core.dto.PageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.RandomUtil;
import com.php25.common.redis.RedisManager;
import com.php25.common.timer.CronExpression;
import com.php25.common.timer.Job;
import com.php25.common.timer.Timer;
import com.php25.qiuqiu.job.constant.JobErrorCode;
import com.php25.qiuqiu.job.dto.BaseRunnable;
import com.php25.qiuqiu.job.dto.JobCreateDto;
import com.php25.qiuqiu.job.dto.JobDto;
import com.php25.qiuqiu.job.dto.JobExecutionCreateDto;
import com.php25.qiuqiu.job.dto.JobExecutionDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/13 18:15
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobModelRepository jobModelRepository;

    private final JobLogRepository jobLogRepository;

    private final JobExecutionRepository jobExecutionRepository;

    private final GroupService groupService;

    private final Timer timer;

    private final JobDtoMapper jobDtoMapper;

    private final RedisManager redisManager;

    private final StreamBridge streamBridge;

    @Value("${server.id}")
    private String serverId;

    @Override
    public PageDto<JobDto> page(String username, String name, Integer pageNum, Integer pageSize) {
        List<Long> groupIds = groupService.findGroupsId(username);
        IPage<JobModel> page = jobModelRepository.page(groupIds, name, pageNum, pageSize);
        List<JobDto> jobDtoList = page.getRecords()
                .stream().map(jobDtoMapper::toDto)
                .collect(Collectors.toList());
        PageDto<JobDto> dataGrid = new PageDto<>();
        dataGrid.setData(jobDtoList);
        dataGrid.setTotal(page.getTotal());
        return dataGrid;
    }

    @Override
    public List<JobDto> findAll(String username) {
        List<Long> groupIds = groupService.findGroupsId(username);
        List<JobModel> list = jobModelRepository.findAll(groupIds);
        return list.stream().map(jobDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Boolean create(String username, JobCreateDto job) {
        Long groupId = groupService.findGroupId(username);
        JobModel jobModel = jobDtoMapper.toEntity(job);
        jobModel.setGroupId(groupId);
        jobModel.setId(RandomUtil.randomUUID());
        jobModelRepository.save(jobModel);
        return true;
    }

    @Override
    public Boolean update(String username, JobUpdateDto job) {
        JobModel jobModel = jobDtoMapper.toEntity(job);
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
    public PageDto<JobLogDto> pageJobLog(String username, String jobName, Integer pageNum, Integer pageSize) {
        List<Long> groupsId = groupService.findGroupsId(username);
        IPage<JobLog> page = jobLogRepository.page(groupsId, jobName, pageNum, pageSize);
        PageDto<JobLogDto> dataGrid = new PageDto<>();
        List<JobLogDto> jobLogDtoList = page.getRecords().stream()
                .map(jobDtoMapper::toDto0)
                .collect(Collectors.toList());
        dataGrid.setData(jobLogDtoList);
        dataGrid.setTotal(page.getTotal());
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
        if (timer.getAllLoadedExecutionIds().contains(jobExecution.getId())) {
            throw Exceptions.throwBusinessException(JobErrorCode.JOB_EXECUTION_HAS_LOADED_CANT_DELETE);
        }
        Long groupId = jobExecution.getGroupId();
        if (groupsId.contains(groupId)) {
            jobExecutionRepository.deleteById(id);
        }
        return true;
    }

    @Override
    public PageDto<JobExecutionDto> pageJobExecution(String username, String jobName, Integer pageNum, Integer pageSize) {
        List<Long> groupIds = groupService.findGroupsId(username);
        IPage<JobExecution> page = this.jobExecutionRepository.page(groupIds, jobName, pageNum, pageSize);
        PageDto<JobExecutionDto> dataGrid = new PageDto<>();
        dataGrid.setTotal(page.getTotal());
        dataGrid.setData(page.getRecords().stream().map(jobDtoMapper::toDto1).collect(Collectors.toList()));
        return dataGrid;
    }

    @Override
    public Boolean refresh(String username, String executionId) {
        Optional<JobExecution> jobExecutionOptional = this.jobExecutionRepository.findById(executionId);
        if (!jobExecutionOptional.isPresent()) {
            return false;
        }
        JobExecution jobExecution = jobExecutionOptional.get();
        Message<String> message = new GenericMessage<>(JsonUtil.toJson(Lists.newArrayList(RandomUtil.randomUUID(), executionId)));
        if (jobExecution.getEnable()) {
            streamBridge.send("timerJobEnabledChannel-in-0",message);
        } else {
            streamBridge.send("timerJobDisabledChannel-in-0",message);
        }
        return true;
    }

    @Override
    public Boolean refreshAll(String username) {
        List<JobExecution> jobExecutions = this.jobExecutionRepository.findAll();
        if (!jobExecutions.isEmpty()) {
            for (JobExecution jobExecution : jobExecutions) {
                this.refresh(username, jobExecution.getId());
            }
        }
        return true;
    }

    @Override
    public void statisticLoadedJobExecutionInfo() {
        Message<String> message = new GenericMessage<>(RandomUtil.randomUUID());
        jobExecutionRepository.resetTimerLoadedNumber();
        streamBridge.send("statisticLoadedJobExecutionChannel-in-0",message);
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
            task = (BaseRunnable) cls.getDeclaredConstructor(String.class, String.class, String.class)
                    .newInstance(jobModel.getId(), jobModel.getName(), executionId);
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


    @Bean
    public Consumer<Message<String>> timerJobEnabledChannel() {
        return message -> {
            log.info("timer_job_enabled:{}", JsonUtil.toJson(message));
            List<String> params = JsonUtil.fromJson(message.getPayload(), new TypeReference<List<String>>() {
            });
            String executionId = params.get(1);
            loadExecution(executionId);
        };

    }


    @Bean
    Consumer<Message<String>> timerJobDisabledChannel() {
        return message -> {
            log.info("timer_job_disabled:{}", JsonUtil.toJson(message));
            List<String> params = JsonUtil.fromJson( message.getPayload(), new TypeReference<List<String>>() {
            });
            String executionId = params.get(1);
            this.timer.stop(executionId);
        };
    }

    @Bean
    Consumer<Message<String>> mergeStatisticLoadedJobExecutionChannel() {
        return message -> {
            JobExecutionStatisticResDto res = JsonUtil.fromJson(message.getPayload().toString(), JobExecutionStatisticResDto.class);
            res.getEntries().forEach((key, value) -> {
                Lock lock = redisManager.lock("merge_statistic_loaded_job_execution");
                lock.lock();
                try {
                    Optional<JobExecution> jobExecutionOptional = jobExecutionRepository.findById(key);
                    if (jobExecutionOptional.isPresent()) {
                        JobExecution jobExecution = jobExecutionOptional.get();
                        JobExecution jobExecution0 = new JobExecution();
                        jobExecution0.setId(jobExecution.getId());
                        jobExecution0.setTimerLoadedNumber(jobExecution.getTimerLoadedNumber() + value);
                        jobExecutionRepository.save(jobExecution0);
                    }
                } finally {
                    lock.unlock();
                }
            });
        };
    }


    @Bean
    Consumer<Message<String>> statisticLoadedJobExecutionChannel() {
        return message -> {
            log.info("JobExecutionStatisticReqDto为:{}", message.getPayload());
            Set<String> executionIds = timer.getAllLoadedExecutionIds();
            Map<String, Integer> res = new HashMap<>();
            for (String executionId : executionIds) {
                res.put(executionId, 1);
            }
            JobExecutionStatisticResDto resDto = new JobExecutionStatisticResDto();
            resDto.setEntries(res);
            Message<String> message0 = new GenericMessage<>(JsonUtil.toJson(resDto));
            streamBridge.send("mergeStatisticLoadedJobExecutionChannel-in-0",message0);
        };

    }
}
