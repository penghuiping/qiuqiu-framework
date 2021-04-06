package com.php25.qiuqiu.workflow.config;

import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author penghuiping
 * @date 2021/3/25 09:02
 */
@Configuration
public class CamundaConfig {

    @Bean
    public SpringProcessEngineConfiguration engineConfiguration(
            DataSource dataSource0,
            PlatformTransactionManager transactionManager0,
            @Value("classpath:loan.bpmn") Resource loan,
            @Value("classpath:dish.dmn") Resource dish
    ) {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setProcessEngineName("engine");
        configuration.setDataSource(dataSource0);
        configuration.setTransactionManager(transactionManager0);
        configuration.setDatabaseSchemaUpdate("true");
        configuration.setDeploymentResources(new Resource[]{loan, dish});

        DefaultDmnEngineConfiguration dmnEngineConfiguration = (DefaultDmnEngineConfiguration)
                DmnEngineConfiguration.createDefaultDmnEngineConfiguration();
        dmnEngineConfiguration.setDefaultInputExpressionExpressionLanguage("javascript");
        configuration.setDmnEngineConfiguration(dmnEngineConfiguration);
        configuration.setJobExecutorActivate(false);
        return configuration;
    }

    @Bean
    public ProcessEngineFactoryBean engineFactory(SpringProcessEngineConfiguration engineConfiguration) {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(engineConfiguration);
        return factoryBean;
    }

    @Bean
    public ProcessEngine processEngine(ProcessEngineFactoryBean factoryBean) throws Exception {
        return factoryBean.getObject();
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public DecisionService decisionService(ProcessEngine processEngine) {
        return processEngine.getDecisionService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

}
