package com.jeet.common.message.exception;

/**
 * 消息推送异常
 */
public class MessageSendException extends Exception {

    public MessageSendException(String msg) {
        super(msg);
    }

    public MessageSendException(Throwable e, String msg) {
        super(msg, e);
    }
}
