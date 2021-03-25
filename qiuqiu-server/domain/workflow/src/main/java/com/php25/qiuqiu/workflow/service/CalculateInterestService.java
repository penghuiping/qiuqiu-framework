package com.php25.qiuqiu.workflow.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * 计算贷款利率
 * @author penghuiping
 * @date 2021/3/25 09:20
 */
@Slf4j
@Service
public class CalculateInterestService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("CalculateInterestService has been invoked");
    }
}
