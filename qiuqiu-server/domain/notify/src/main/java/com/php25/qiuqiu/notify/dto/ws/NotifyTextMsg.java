package com.php25.qiuqiu.notify.dto.ws;

import com.php25.common.ws.BaseRetryMsg;
import com.php25.common.ws.annotation.WsMsg;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/9 10:15
 */
@Setter
@Getter
@WsMsg(action = "notify_text")
public class NotifyTextMsg extends BaseRetryMsg {
    private String content;
}
