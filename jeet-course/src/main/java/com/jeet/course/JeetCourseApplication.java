package com.jeet.course;

import com.jeet.common.security.annotation.EnableCustomConfig;
import com.jeet.system.api.annotation.EnableJEETFeignClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.jeet.course.**.mapper")
@EnableCustomConfig
@SpringBootApplication
@EnableJEETFeignClients
public class JeetCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeetCourseApplication.class, args);
    }

}
