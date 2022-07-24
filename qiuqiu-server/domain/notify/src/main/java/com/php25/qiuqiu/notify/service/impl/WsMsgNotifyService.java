package com.php25.qiuqiu.notify.service.impl;

import com.php25.common.ws.core.SessionContext;
import com.php25.qiuqiu.notify.service.MsgNotifyService;
import com.php25.qiuqiu.notify.dto.ws.NotifyTextMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 消息通知-websocket方式
 *
 * @author penghuiping
 * @date 2021/3/9 10:10
 */
@Service
@RequiredArgsConstructor
public class WsMsgNotifyService implements MsgNotifyService {

    private final SessionContext globalSession;


    @Override
    public boolean broadcastTextMsg(String msg) {
        NotifyTextMsg notifyTextMsg = new NotifyTextMsg();
        notifyTextMsg.setMsgId(UUID.randomUUID().toString());
        notifyTextMsg.setContent(msg);
        notifyTextMsg.setSessionId(null);
        notifyTextMsg.setTimestamp(System.currentTimeMillis());
        globalSession.send(notifyTextMsg);
        return true;
    }
}
