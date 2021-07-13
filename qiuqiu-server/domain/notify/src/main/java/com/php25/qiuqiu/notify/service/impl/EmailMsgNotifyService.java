package com.php25.qiuqiu.notify.service.impl;

import com.php25.qiuqiu.notify.service.MsgNotifyService;

/**
 * 消息通知-邮箱方式
 * @author penghuiping
 * @date 2021/3/9 10:11
 */
public class EmailMsgNotifyService implements MsgNotifyService {

    @Override
    public boolean broadcastTextMsg(String msg) {
        return false;
    }
}
