package com.php25.common.ws.handler;

import com.php25.common.ws.BaseRetryMsg;
import com.php25.common.ws.GlobalSession;

/**
 * 消息处理接口
 *
 * @author penghuiping
 * @date 2020/08/10
 */
public interface MsgHandler<T extends BaseRetryMsg> {

    void handle(GlobalSession session, T msg) throws Exception;

}
