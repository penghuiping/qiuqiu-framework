package com.php25.qiuqiu.monitor.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author penghuiping
 * @date 2019/12/21 17:19
 */
interface AuditLogInput {
    String INPUT = "audit_log_input";

    @Input(AuditLogInput.INPUT)
    SubscribableChannel input();
}

interface AuditLogOutput {
    String OUTPUT = "audit_log_output";

    @Output(AuditLogOutput.OUTPUT)
    MessageChannel output();
}

public interface AuditLogProcessor extends AuditLogInput, AuditLogOutput {
}


