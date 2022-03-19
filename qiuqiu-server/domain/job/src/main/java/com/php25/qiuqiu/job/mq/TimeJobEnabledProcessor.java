package com.php25.qiuqiu.job.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penghuiping
 * @date 2019/12/21 17:19
 */
interface TimeJobEnabledOutput {
    String OUTPUT = "time_job_enabled_output";

    @Output(TimeJobEnabledOutput.OUTPUT)
    MessageChannel output();
}


interface TimeJobEnabledInput {
    String INPUT = "time_job_enabled_input";

    @Input(TimeJobEnabledInput.INPUT)
    SubscribableChannel input();
}

public interface TimeJobEnabledProcessor extends TimeJobEnabledInput, TimeJobEnabledOutput {
}


