package com.php25.common.ws;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author penghuiping
 * @date 2021/3/9 09:22
 */
@Getter
public class ExpirationSessionId implements Delayed {

    private String sessionId;

    private long timestamp;

    public ExpirationSessionId(String sessionId, long timestamp) {
        this.sessionId = sessionId;
        this.timestamp = timestamp;
    }

    /**
     * 默认30秒没有收到心跳，断开连接
     */
    private long timeout = 30000;

    @Override
    public long getDelay(TimeUnit unit) {
        return (timeout - (System.currentTimeMillis() - getTimestamp())) * 1000000;
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS));
    }
}