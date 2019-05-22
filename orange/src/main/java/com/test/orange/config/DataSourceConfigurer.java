package com.test.orange.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by yangkang on 2019/5/13
 */
@Configuration
public class DataSourceConfigurer {
    @Bean
    public DataSource dataSource(Environment environment) {
        return DruidDataSourceBuilder.create().build(environment,"spring.datasource.druid.");
    }
}
