package com.php25.common.ws;


import com.php25.common.core.mess.SpringContextHolder;
import lombok.extern.log4j.Log4j2;

/**
 * @author penghuiping
 * @date 2021/3/14 11:41
 */
@Log4j2
public class ExpirationSessionCallback implements Runnable {

    private String sessionId;

    private GlobalSession globalSession;

    public ExpirationSessionCallback(String sessionId) {
        this.sessionId = sessionId;
        this.globalSession = SpringContextHolder.getBean0(GlobalSession.class);
    }

    @Override
    public void run() {
        try {
            ConnectionClose connectionClose = new ConnectionClose();
            connectionClose.setCount(1);
            connectionClose.setMsgId(globalSession.generateUUID());
            connectionClose.setSessionId(sessionId);
            globalSession.send(connectionClose);
        }catch (Exception e) {
            log.error("未接受到心跳包，关闭session出错",e);
        }

    }
}
