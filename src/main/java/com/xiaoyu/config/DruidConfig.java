package com.xiaoyu.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 17:04
 * druid 数据源注入
 */

@Configuration
@Slf4j
public class DruidConfig {

    /**
        将自定义的 Druid数据源添加到容器中，不再让 SpringBoot 自动创建
        绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
        @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
        前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
    */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        log.info("druid 配置已启用!");
        return new DruidDataSource();
    }




}
