package com.php25.common.ws.protocal;

import com.php25.common.ws.BaseRetryMsg;
import com.php25.common.ws.annotation.WsMsg;

/**
 * @author penghuiping
 * @date 2020/8/17 16:47
 */
@WsMsg(action = "ping")
public class Ping extends BaseRetryMsg {
}
