package com.jeet.task.job;

import org.springframework.stereotype.Component;

@Component("jeetTask")
public class JeetTask {

    public void params(String params) {
        System.out.println("执行有参方法: " + params);
    }
}
