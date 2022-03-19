package com.php25.qiuqiu.job.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penghuiping
 * @date 2022/3/19 18:52
 */
interface MergeStatisticLoadedJobOutput {
    String OUTPUT = "merge_statistic_loaded_job_output";

    @Output(MergeStatisticLoadedJobOutput.OUTPUT)
    MessageChannel output();
}


interface MergeStatisticLoadedJobInput {
    String INPUT = "merge_statistic_loaded_job_input";

    @Input(MergeStatisticLoadedJobInput.INPUT)
    SubscribableChannel input();
}

public interface MergeStatisticLoadedJobProcessor extends MergeStatisticLoadedJobOutput,MergeStatisticLoadedJobInput {
}
