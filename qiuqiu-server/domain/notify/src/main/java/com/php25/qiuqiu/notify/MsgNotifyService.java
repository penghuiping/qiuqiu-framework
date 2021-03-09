package com.php25.qiuqiu.notify;

/**
 * 消息通知
 *
 * @author penghuiping
 * @date 2021/3/9 10:08
 */
public interface MsgNotifyService {

    /**
     * 广播文本消息
     *
     * @param msg 文本消息
     * @return true: 通知成功
     */
    public boolean broadcastTextMsg(String msg);
}
