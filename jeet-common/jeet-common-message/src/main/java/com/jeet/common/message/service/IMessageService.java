package com.jeet.common.message.service;

import com.jeet.common.message.exception.MessageSendException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 消息服务接口
 */
public interface IMessageService {

    /**
     * 建立连接
     * @param receiver
     */
    SseEmitter connection(String receiver);

    /**
     * 发送消息
     * @param receiver 接收者id
     * @param message 发送的消息内容
     */
    void send(String receiver, String message);

    /**
     * 发送给所有人
     * @param message
     * @throws MessageSendException
     */
    void sendAllUser(String message);
}
