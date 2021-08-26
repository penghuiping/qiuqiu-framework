package com.php25.common.ws;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.redis.RList;
import com.php25.common.redis.RedisManager;
import com.php25.common.ws.config.Constants;
import com.php25.common.ws.retry.InnerMsgRetryQueue;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * redis队列消息订阅者,这里使用轮询方式从redis队列中pull消息
 *
 * @author penghuiping
 * @date 2020/08/10
 */
@Slf4j
public class RedisQueueSubscriber implements InitializingBean, ApplicationListener<ContextClosedEvent> {

    private final RedisManager redisManager;

    private final String serverId;

    private final InnerMsgRetryQueue innerMsgRetryQueue;

    private ExecutorService singleThreadExecutor;

    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public RedisQueueSubscriber(RedisManager redisManager, String serverId, InnerMsgRetryQueue innerMsgRetryQueue) {
        this.redisManager = redisManager;
        this.serverId = serverId;
        this.innerMsgRetryQueue = innerMsgRetryQueue;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.singleThreadExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("ws-redis-queue-subscriber-%d")
                        .build());
        this.run();
    }

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent contextClosedEvent) {
        try {
            isRunning.compareAndSet(true, false);
            this.singleThreadExecutor.shutdown();
            boolean res = this.singleThreadExecutor.awaitTermination(3, TimeUnit.SECONDS);
            if (res) {
                log.info("关闭ws:RedisQueueSubscriber成功");
            }
        } catch (InterruptedException e) {
            log.error("关闭ws:RedisQueueSubscriber出错", e);
            Thread.currentThread().interrupt();
        }
        unRegisterRedisQueue();
    }

    public void run() {
        this.singleThreadExecutor.execute(() -> {
            while (isRunning.get()) {
                try {
                    RList<String> rList = redisManager.list(Constants.prefix + this.serverId, String.class);
                    String msg = rList.blockRightPop(1, TimeUnit.SECONDS);
                    if (!StringUtil.isBlank(msg)) {
                        BaseRetryMsg baseRetry = JsonUtil.fromJson(msg, BaseRetryMsg.class);
                        innerMsgRetryQueue.put(baseRetry);
                    }
                } catch (Exception e) {
                    log.error("轮训获取redis队列中的消息出错", e);
                }
            }
        });
    }

    private void unRegisterRedisQueue() {
        log.info("unregister redis Queue...;serverId:{}", serverId);
        redisManager.remove(Constants.prefix + serverId);
    }
}
