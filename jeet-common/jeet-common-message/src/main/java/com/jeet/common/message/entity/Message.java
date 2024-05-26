package com.jeet.common.message.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息实体
 * @author zhangxiujin
 */
@Getter
@Setter
public class Message {
    /**
     * 消息类型 (advice 通知消息， queue 队列消息)
     */
    private String type;
    /**
     * 消息发送者id
     */
    private Long sender;
    /**
     * 消息内容
     */
    private String content;

}
