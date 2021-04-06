package com.php25.qiuqiu.admin.controller;

import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作流与规则引擎测试
 * @author penghuiping
 * @date 2021/3/25 09:27
 */
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController extends JSONController {

    private final RuntimeService runtimeService;

    private final DecisionService decisionService;

    /**
     * 工作流测试
     * @since v1
     */
    @APIVersion("v1")
    @GetMapping("/test")
    public JSONResponse<Boolean> test() {
        runtimeService.startProcessInstanceByKey("loan");
        return succeed(true);
    }

    /**
     * 规则引擎测试
     * @since v1
     */
    @APIVersion("v1")
    @GetMapping("/test1")
    public JSONResponse<String> test1(@RequestParam("season") String season,@RequestParam("number") Integer number,@RequestParam("weight") Float weight) {
        VariableMap variables = Variables.createVariables()
                .putValue("季节", season)
                .putValue("顾客数", number)
                .putValue("权重", weight);
        DmnDecisionTableResult dishDecisionResult = decisionService.evaluateDecisionTableByKey("dish", variables);
        String desiredDish = dishDecisionResult.getSingleEntry();
        return succeed(desiredDish);
    }
}
