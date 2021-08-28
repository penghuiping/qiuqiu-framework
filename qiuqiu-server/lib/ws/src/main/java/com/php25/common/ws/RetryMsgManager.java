package com.php25.common.ws;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.SpringContextHolder;
import com.php25.common.core.util.JsonUtil;
import com.php25.common.ws.protocal.BaseMsg;
import com.php25.common.ws.protocal.BaseNoRetryMsg;
import com.php25.common.ws.protocal.BaseRetryMsg;
import com.php25.common.ws.retry.DefaultRetryQueue;
import com.php25.common.ws.retry.RejectAction;
import com.php25.common.ws.retry.RetryQueue;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 消息重发器,此类用于实现延时消息重发
 * 对于服务端push消息，客户端需要回ack,如果服务端没有收到客户端回的ack的消息,则服务端需要重发
 * 服务端默认5秒没收到ack消息,进行原消息重发
 * 最大重发次数为5次
 *
 * @author penghuiping
 * @date 2021/8/26 15:56
 */
@Log4j2
public class RetryMsgManager implements InitializingBean, ApplicationListener<ContextClosedEvent> {

    private final BlockingQueue<BaseNoRetryMsg> noDelayQueue = new LinkedBlockingQueue<>();

    private final RetryQueue<BaseRetryMsg> retryQueue;

    private final ExecutorService singleThreadExecutorNoDelay;

    private SessionContext globalSession;

    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public RetryMsgManager() {
        this.singleThreadExecutorNoDelay = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryBuilder().setNameFormat("ws-delay-queue-nodelay-subscriber-%d")
                        .build());

        this.retryQueue = new DefaultRetryQueue<>(5, 5000L,
                retryMsg -> {
                    //重试逻辑
                    ExpirationSocketSession expirationSocketSession = this.getGlobalSession().getExpirationSocketSession(retryMsg.getSessionId());
                    if (null != expirationSocketSession) {
                        expirationSocketSession.put(retryMsg);
                    }
                },
                retryMsg -> {
                    //重试超过5次拒绝逻辑
                    log.warn("此消息重试超过五次依旧失败:{}", JsonUtil.toJson(retryMsg));
                });
    }

    public SessionContext getGlobalSession() {
        if (this.globalSession == null) {
            this.globalSession = SpringContextHolder.getBean0(SessionContext.class);
        }
        return globalSession;
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
                BaseNoRetryMsg msg = null;
                try {
                    msg = noDelayQueue.poll(2, TimeUnit.SECONDS);
                    if (null != msg) {
                        ExpirationSocketSession expirationSocketSession = getGlobalSession().getExpirationSocketSession(msg.getSessionId());
                        if (null != expirationSocketSession) {
                            expirationSocketSession.put(msg);
                        }
                    }
                } catch (Exception e) {
                    log.error("消息重发出错:{}", JsonUtil.toJson(msg), e);
                }
            }
        });
    }

    public void put(BaseMsg baseMsg) {
        //判断是否需要重试
        if (baseMsg instanceof BaseRetryMsg) {
            //需要重试
            BaseRetryMsg baseRetry = (BaseRetryMsg) baseMsg;
            retryQueue.offer(baseRetry.getMsgId(), baseRetry);
        } else if (baseMsg instanceof BaseNoRetryMsg) {
            //不需要重试
            BaseNoRetryMsg baseNoRetryMsg = (BaseNoRetryMsg) baseMsg;
            noDelayQueue.offer(baseNoRetryMsg);
        } else {
            throw Exceptions.throwImpossibleException();
        }
    }

    public void put(BaseRetryMsg baseMsg, RejectAction<BaseRetryMsg> rejectAction) {
        //需要重试
        BaseRetryMsg baseRetry = baseMsg;
        retryQueue.offer(baseRetry.getMsgId(), baseRetry, rejectAction);
    }

    public void remove(String msgId) {
        retryQueue.remove(msgId);
    }

    public BaseRetryMsg get(String msgId) {
        return retryQueue.get(msgId);
    }

    public void stats() {
        log.info("InnerMsgRetryQueue noDelayQueue:{}", noDelayQueue.size());
        log.info("InnerMsgRetryQueue msgs:{}", retryQueue.size());
    }
}
