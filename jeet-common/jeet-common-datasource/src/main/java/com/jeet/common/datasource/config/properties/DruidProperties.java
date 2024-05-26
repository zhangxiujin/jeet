package com.jeet.common.datasource.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Desc: DruidProperties
 * @author: qingmei
 * @Date 2023/7/21 11:05
 */
@Configuration
public class DruidProperties {

    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;

    public DruidDataSource dataSource(DruidDataSource dataSource) {
        dataSource.setInitialSize(initialSize);
        return dataSource;
    }
}
