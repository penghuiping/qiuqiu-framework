package com.php25.common.ws;

import com.php25.common.core.util.JsonUtil;
import com.php25.common.ws.protocal.BaseMsg;
import com.php25.common.ws.protocal.BaseRetryMsg;
import com.php25.common.ws.protocal.ConnectionClose;
import com.php25.common.ws.protocal.ConnectionCreate;
import com.php25.common.ws.protocal.Ping;
import com.php25.common.ws.protocal.RequestAuthInfo;
import com.php25.common.ws.retry.RejectAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * websocket的消息通讯的入口
 *
 * @author penghuiping
 * @date 2021-08-28
 */
@Slf4j
public class WebsocketHandler extends TextWebSocketHandler {


    private final RetryMsgManager retryMsgManager;

    public WebsocketHandler(RetryMsgManager retryMsgManager) {
        this.retryMsgManager = retryMsgManager;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        SessionContext sessionContext = retryMsgManager.getGlobalSession();
        String payload = message.getPayload();
        BaseMsg baseMsg = JsonUtil.fromJson(payload, BaseMsg.class);
        if (!(baseMsg instanceof Ping)) {
            log.info("ws request msg:{}", JsonUtil.toJson(baseMsg));
        }
        ExpirationSocketSession expirationSocketSession = sessionContext.getExpirationSocketSession(session);
        if (null == expirationSocketSession) {
            log.info("expirationSocketSession is null:{}", JsonUtil.toJson(baseMsg));
            return;
        }
        baseMsg.setSessionId(expirationSocketSession.getSessionId());
        if (baseMsg instanceof RequestAuthInfo) {
            retryMsgManager.put((RequestAuthInfo) baseMsg, value -> {
                ConnectionClose connectionClose = new ConnectionClose();
                connectionClose.setMsgId(sessionContext.generateUUID());
                connectionClose.setSessionId(value.getSessionId());
                retryMsgManager.put(connectionClose);
            });
        } else {
            retryMsgManager.put(baseMsg);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        ConnectionClose connectionClose = new ConnectionClose();
        connectionClose.setMsgId(retryMsgManager.getGlobalSession().generateUUID());
        ExpirationSocketSession expirationSocketSession = retryMsgManager.getGlobalSession().getExpirationSocketSession(session);
        connectionClose.setSessionId(expirationSocketSession.getSessionId());
        retryMsgManager.put(connectionClose);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //websocket连接建立
        retryMsgManager.getGlobalSession().create(session);
        ConnectionCreate connectionCreate = new ConnectionCreate();
        connectionCreate.setMsgId(retryMsgManager.getGlobalSession().generateUUID());
        ExpirationSocketSession expirationSocketSession = retryMsgManager.getGlobalSession().getExpirationSocketSession(session);
        connectionCreate.setSessionId(expirationSocketSession.getSessionId());
        retryMsgManager.put(connectionCreate);
    }


}
