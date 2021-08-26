package com.php25.common.ws.retry;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.php25.common.core.mess.SpringContextHolder;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.timer.Job;
import com.php25.common.timer.Timer;
import com.php25.common.ws.BaseRetryMsg;
import com.php25.common.ws.ExpirationSocketSession;
import com.php25.common.ws.GlobalSession;
import com.php25.common.ws.protocal.Ping;
import com.php25.common.ws.protocal.Pong;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 消息重发器,此类用于实现延时消息重发
 * 对于没有收到ack的消息,需要重发,
 * 默认5秒没收到ack消息,进行原消息重发
 * 最大重发次数为5次
 *
 * @author penghuiping
 * @date 20/8/11 10:50
 */
@Slf4j
public class InnerMsgRetryQueue implements InitializingBean, ApplicationListener<ContextClosedEvent> {

    private final BlockingQueue<BaseRetryMsg> noDelayQueue = new LinkedBlockingQueue<>();

    private final Cache<String, BaseRetryMsg> msgs;

    private final ExecutorService singleThreadExecutorNoDelay;

    private GlobalSession globalSession;

    private Timer delayQueue;

    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public InnerMsgRetryQueue() {
        msgs = Caffeine.newBuilder()
                .maximumSize(8196)
                .expireAfterWrite(Duration.ofMinutes(1))
                .build();
        this.singleThreadExecutorNoDelay = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("ws-delay-queue-nodelay-subscriber-%d")
                        .build());
    }

    public GlobalSession getGlobalSession() {
        if (this.globalSession == null) {
            this.globalSession = SpringContextHolder.getBean0(GlobalSession.class);
        }
        return globalSession;
    }

    public Timer getDelayQueue() {
        if (this.delayQueue == null) {
            this.delayQueue = SpringContextHolder.getBean0(Timer.class);
        }
        return delayQueue;
    }

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent contextClosedEvent) {
        try {
            isRunning.compareAndSet(true, false);
            this.singleThreadExecutorNoDelay.shutdown();
            boolean res = this.singleThreadExecutorNoDelay.awaitTermination(3, TimeUnit.SECONDS);
            if (res) {
                log.info("关闭ws:singleThreadExecutorNoDelay成功");
            }
        } catch (InterruptedException e) {
            log.error("关闭ws:singleThreadExecutorNoDelay出错", e);
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        run();
    }

    public void run() {
        this.singleThreadExecutorNoDelay.execute(() -> {
            while (isRunning.get()) {
                BaseRetryMsg msg = null;
                try {
                    msg = noDelayQueue.poll(2, TimeUnit.SECONDS);
                    if (null != msg) {
                        if (msg.getCount() <= msg.getMaxRetry()) {
                            ExpirationSocketSession expirationSocketSession = getGlobalSession().getExpirationSocketSession(msg.getSessionId());
                            if (null != expirationSocketSession) {
                                expirationSocketSession.put(msg);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("消息重发出错:{}", JsonUtil.toJson(msg), e);
                }
            }
        });
    }


    public void put(BaseRetryMsg baseRetry) {
        if (baseRetry.getInterval() > 0) {
            InnerMsgRetryCallback callback = new InnerMsgRetryCallback(baseRetry);
            Job job = new Job(baseRetry.getMsgId() + baseRetry.getAction(), baseRetry.getInterval() + baseRetry.getTimestamp(), callback);
            getDelayQueue().add(job);
        } else {
            noDelayQueue.offer(baseRetry);
        }

        if (!(baseRetry instanceof Ping || baseRetry instanceof Pong)) {
            msgs.put(baseRetry.getMsgId() + baseRetry.getAction(), baseRetry);
        }
    }

    public void remove(BaseRetryMsg baseRetry) {
        msgs.invalidate(baseRetry.getMsgId() + baseRetry.getAction());
    }

    public BaseRetryMsg get(String msgId, String action) {
        return msgs.getIfPresent(msgId + action);
    }

    public void stats() {
        log.info("InnerMsgRetryQueue noDelayQueue:{}", noDelayQueue.size());
        log.info("InnerMsgRetryQueue msgs:{}", msgs.estimatedSize());
    }
}
