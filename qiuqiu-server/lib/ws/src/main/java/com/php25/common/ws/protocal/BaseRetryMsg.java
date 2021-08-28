package com.php25.common.ws.protocal;

import lombok.Getter;
import lombok.Setter;

/**
 * 所有需要重发的消息需要继承此抽象类
 *
 * @author penghuiping
 * @date 20/8/11 10:58
 */

@Setter
@Getter
public abstract class BaseRetryMsg extends BaseMsg {

}
