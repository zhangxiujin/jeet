package com.jeet.task;

import com.jeet.common.security.annotation.EnableCustomConfig;
import com.jeet.system.api.annotation.EnableJEETFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan("com.jeet.task.**.mapper")
@EnableCustomConfig
@EnableJEETFeignClients
@SpringBootApplication
public class JeetTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeetTaskApplication.class, args);
    }

}
