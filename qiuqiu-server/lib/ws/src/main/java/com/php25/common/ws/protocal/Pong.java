package com.php25.common.ws.protocal;

import com.php25.common.ws.BaseRetryMsg;
import com.php25.common.ws.annotation.WsMsg;

/**
 * @author penghuiping
 * @date 2020/8/17 16:49
 */
@WsMsg(action = "pong")
public class Pong extends BaseRetryMsg {
}
