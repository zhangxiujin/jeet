package com.jeet.common.message.service.impl;

import com.jeet.common.message.exception.MessageSendException;
import com.jeet.common.message.service.IMessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class SSEMessageServiceImpl implements IMessageService {
    private Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    @Override
    public SseEmitter connection(String receiver) {
        synchronized (String.valueOf(receiver).intern()) {
            // 设置超时时间, 0表示不超时
            SseEmitter sseEmitter = new SseEmitter(1000 * 60 * 60L);
            try {
                sseEmitter.send(SseEmitter.event().name("open")); //触发前端eventsource的open事件
            } catch (IOException e) {
                e.printStackTrace();
            }
            sseEmitter.onCompletion(completionCallback(receiver));
            sseEmitter.onError(errorCallback(receiver));
            sseEmitter.onTimeout(timeoutCallback(receiver));
            sseEmitterMap.put(receiver, sseEmitter);
            return sseEmitter;
        }
    }

    @Override
    public void send(String receiver, String message) {
        synchronized (String.valueOf(receiver).intern()) {
            try {
                if(receiver == null) {
                    throw new MessageSendException("消息接收者为空");
                }
                if(!sseEmitterMap.containsKey(receiver)) {
                    throw new MessageSendException("无法找到消息接收者");
                }
                SseEmitter sseEmitter = sseEmitterMap.get(receiver);
                sseEmitter.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送给所有人
     */
    @Override
    public void sendAllUser(String message) {
        Collection<SseEmitter> values = sseEmitterMap.values();
        Iterator<SseEmitter> iterator = values.iterator();
        while (iterator.hasNext()) {
            SseEmitter sseEmitter = iterator.next();
            try {
                sseEmitter.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable timeoutCallback(String receiverId) {
        return () -> System.out.println("消息发送超时");
    }

    private Consumer<Throwable> errorCallback(String receiverId) {
        return throwable -> {
            System.out.println("消息发送报错，接收者ID：" + receiverId);
            // 执行其他错误操作，例如记录日志、发送通知等
        };
    }

    private Runnable completionCallback(String receiverId) {
        return () -> {
            sseEmitterMap.remove(receiverId);
            System.out.println("连接被关闭");
        };
    }
}
