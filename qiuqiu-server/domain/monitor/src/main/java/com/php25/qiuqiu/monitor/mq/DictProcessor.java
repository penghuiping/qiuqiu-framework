package com.php25.qiuqiu.monitor.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penghuiping
 * @date 2019/12/21 17:19
 */
interface DictInput {
    String INPUT = "dict_input";

    @Input(DictInput.INPUT)
    SubscribableChannel input();
}

interface DictOutput {
    String OUTPUT = "dict_output";

    @Output(DictOutput.OUTPUT)
    MessageChannel output();
}


public interface DictProcessor extends DictInput,DictOutput {
}


