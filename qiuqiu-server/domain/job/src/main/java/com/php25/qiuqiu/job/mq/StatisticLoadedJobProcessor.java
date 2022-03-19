package com.php25.qiuqiu.job.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penghuiping
 * @date 2022/3/19 18:56
 */
interface StatisticLoadedJobOutput {
    String OUTPUT = "statistic_loaded_job_output";

    @Output(StatisticLoadedJobOutput.OUTPUT)
    MessageChannel output();
}


interface StatisticLoadedJobInput {
    String INPUT = "statistic_loaded_job_input";

    @Input(StatisticLoadedJobInput.INPUT)
    SubscribableChannel input();
}

public interface StatisticLoadedJobProcessor extends StatisticLoadedJobOutput,StatisticLoadedJobInput {
}
