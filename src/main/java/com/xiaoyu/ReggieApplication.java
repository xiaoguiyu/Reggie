package com.xiaoyu;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 17:10
 */

@Slf4j
@SpringBootApplication
@MapperScan("com.xiaoyu.mapper")
@EnableTransactionManagement   // 开启service层的事务功能
public class ReggieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功!");
    }

}
