package com.php25.qiuqiu.notify.service.impl;

import com.php25.common.core.mess.IdGenerator;
import com.php25.common.ws.GlobalSession;
import com.php25.qiuqiu.notify.service.MsgNotifyService;
import com.php25.qiuqiu.notify.dto.ws.NotifyTextMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 消息通知-websocket方式
 *
 * @author penghuiping
 * @date 2021/3/9 10:10
 */
@Service
@RequiredArgsConstructor
public class WsMsgNotifyService implements MsgNotifyService {

    private final GlobalSession globalSession;

    private final IdGenerator idGenerator;

    @Override
    public boolean broadcastTextMsg(String msg) {
        NotifyTextMsg notifyTextMsg = new NotifyTextMsg();
        notifyTextMsg.setMsgId(idGenerator.getUUID());
        notifyTextMsg.setContent(msg);
        notifyTextMsg.setMaxRetry(1);
        notifyTextMsg.setSessionId(null);
        notifyTextMsg.setInterval(0);
        notifyTextMsg.setTimestamp(System.currentTimeMillis());
        globalSession.send(notifyTextMsg,false);
        return false;
    }
}
