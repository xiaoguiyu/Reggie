package com.xiaoyu;

import com.xiaoyu.pojo.entity.Employee;
import com.xiaoyu.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/3 18:53
 */

@SpringBootTest
public class AddEmployeeTest {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 批量插入数据
     */
    @Test
    public void addEmployee() {

        for (int i = 1; i < 20; i++) {
            Employee employee = new Employee();
            employee.setName("测试" + i);
            employee.setUsername("test" + i );
            String password =  DigestUtils.md5DigestAsHex("123456".getBytes());
            employee.setPassword(password);
            if (i >= 10) {
                employee.setPhone("131751700" + i);
                employee.setIdNumber("6101251998000000" + i);
            } else {
                employee.setPhone("1317517800" + i);
                employee.setIdNumber("61012519980000000" + i);
            }
            employee.setSex("0");
            employee.setCreateTime(LocalDateTime.now());
            employee.setUpdateTime(LocalDateTime.now());
            employee.setCreateUser(1L);
            employee.setUpdateUser(1L);
            employeeService.save(employee);
        }

    }

}
