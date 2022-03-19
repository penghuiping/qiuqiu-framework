package com.php25.qiuqiu.job.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penghuiping
 * @date 2022/3/19 18:46
 */
interface TimeJobDisabledOutput {
    String OUTPUT = "time_job_disabled_output";

    @Output(TimeJobDisabledOutput.OUTPUT)
    MessageChannel output();
}


interface TimeJobDisabledInput {
    String INPUT = "time_job_disabled_input";

    @Input(TimeJobDisabledInput.INPUT)
    SubscribableChannel input();
}


public interface TimeJobDisabledProcessor extends TimeJobDisabledOutput,TimeJobDisabledInput{
}
