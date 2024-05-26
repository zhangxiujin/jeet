package com.jeet.common.message.controller;

import com.jeet.common.message.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    /**
     * 连接消息服务器
     * @return
     */
    @GetMapping(value = "/conn/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connection(@PathVariable(name = "receiver") String receiver) {
        return messageService.connection(receiver);
    }

}
