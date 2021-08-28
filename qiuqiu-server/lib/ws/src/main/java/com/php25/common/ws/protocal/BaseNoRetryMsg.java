package com.php25.common.ws.protocal;

import lombok.Getter;
import lombok.Setter;

/**
 * 不需要重试的消息,需要基础此基类
 *
 * @author penghuiping
 * @date 2021/8/26 16:02
 */
@Setter
@Getter
public abstract class BaseNoRetryMsg extends BaseMsg {
}
