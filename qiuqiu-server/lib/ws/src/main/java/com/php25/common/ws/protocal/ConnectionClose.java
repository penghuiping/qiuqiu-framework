package com.php25.common.ws.protocal;

import com.php25.common.ws.BaseRetryMsg;
import com.php25.common.ws.annotation.WsMsg;

/**
 * @author penghuiping
 * @date 20/8/12 16:51
 */
@WsMsg(action = "connection_close")
public class ConnectionClose extends BaseRetryMsg {
}
