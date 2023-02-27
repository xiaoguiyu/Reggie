package com.xiaoyu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootTest
class ReggieApplicationTests {

    @Resource
    private JdbcTemplate jdbcTemplate;




    // 数据库连接测试
    @Test
    void contextLoads() {

        String sql = "select * from address_book";

        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }


    @Test
    void insert() {

        String sql = "insert into ";
        jdbcTemplate.execute(sql);


    }



    @Test
    void update() {

    }


    @Test
    void delete() {

    }



}
