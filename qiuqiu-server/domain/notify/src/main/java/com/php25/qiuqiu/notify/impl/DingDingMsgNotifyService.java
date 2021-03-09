package com.php25.qiuqiu.notify.impl;

import com.php25.qiuqiu.notify.MsgNotifyService;

/**
 * 消息通知-钉钉方式
 *
 * @author penghuiping
 * @date 2021/3/9 10:12
 */
public class DingDingMsgNotifyService implements MsgNotifyService {
    @Override
    public boolean broadcastTextMsg(String msg) {
        return false;
    }
}
