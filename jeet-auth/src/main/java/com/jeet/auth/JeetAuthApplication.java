package com.jeet.auth;

import com.jeet.system.api.annotation.EnableJEETFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableJEETFeignClients
@SpringBootApplication
public class JeetAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeetAuthApplication.class, args);
    }

}
