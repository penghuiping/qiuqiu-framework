package com.php25.common.ws;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息分发器
 *
 * @author penghuiping
 * @date 2020/08/10
 */
@Slf4j
public class MsgDispatcher {

    private final ConcurrentHashMap<String, MsgHandler<BaseRetryMsg>> handlers = new ConcurrentHashMap<>();

    private GlobalSession session;

    private final ConcurrentHashMap<String, ReplyAckHandler> ackHandlers = new ConcurrentHashMap<>();

    public void setSession(GlobalSession session) {
        this.session = session;
    }

    public void dispatch(BaseRetryMsg baseRetry) {
        try {
            String action = baseRetry.getAction();
            MsgHandler<BaseRetryMsg> handler = handlers.get(action);
            if (null != handler) {
                handler.handle(session, baseRetry);
            }
        } catch (Exception e) {
            log.error("发送websocket消息出错", e);
        }
    }

    public void registerHandler(String name, MsgHandler<BaseRetryMsg> msgHandler) {
        handlers.put(name, msgHandler);
    }

    public void registerAckHandler(String action, ReplyAckHandler ackHandler) {
        ackHandlers.put(action, ackHandler);
    }

    public void dispatchAck(String action,BaseRetryMsg srcMsg) {
        try {
            ReplyAckHandler replyAckHandler = ackHandlers.get(action);
            if (null != replyAckHandler) {
                replyAckHandler.handle(session, srcMsg);
            }
        } catch (Exception e) {
            log.error("发送websocket消息出错", e);
        }
    }

}
