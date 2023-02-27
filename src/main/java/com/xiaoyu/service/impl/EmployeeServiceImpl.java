package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.mapper.EmployeeMapper;
import com.xiaoyu.pojo.entity.Employee;
import com.xiaoyu.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 23:21
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


}
