package com.jeet.system;

import com.jeet.common.security.annotation.EnableCustomConfig;
import com.jeet.system.api.annotation.EnableJEETFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableCustomConfig
@EnableJEETFeignClients
@SpringBootApplication
@MapperScan(basePackages = "com.jeet.system.dao")
public class JeetSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeetSystemApplication.class, args);
    }

}
