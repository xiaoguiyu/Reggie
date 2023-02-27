package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.entity.Employee;
import com.xiaoyu.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/9/25 23:32
 * 处理employee 的所有请求
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    /**
     * 登录
     * @param session 存储用户的id
     * @param employee 接收前端传入的账号和密码
     * @return 返回一个处理结果的 Result 对象
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpSession session, @RequestBody Employee employee) {

        //将提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password =  DigestUtils.md5DigestAsHex(password.getBytes());

        // 根据提交的用户名来在数据查询密码
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);  // 从数据库查询到的结果

        // 没有查询到结果则返回失败的结果
        if (emp == null) {
            return Result.error("用户名不存在!");
        }

        // 密码验证，如果不一致则返回登录失败结果
        if (!password.equals(emp.getPassword())) {
            return Result.error("密码错误!");
        }

        // 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return Result.error("用户已被禁用!");
        }

        // 登录成功，将员工id存入Session并返回登录成功结果
        session.setAttribute("employee", emp.getId());
        return Result.success(emp);
    }


    /**
     * 注销
     * @param session 用于移除用户的session
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {

        // 移除用户的session
        session.removeAttribute("employee");
        return Result.success("注销成功");
    }


    /**
     * 添加用户
     * @param employee 前端表单传递的参数
     */
    @PostMapping
    public Result<String> addEmployee(@RequestBody Employee employee) {

        // 初始化用户的密码 为123456, 并使用MD5加密
        String password =  DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);


        // 这里使用了自动填充公共字段, 可以不在手动设置
        // 设置用户的创建和更新的时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        //设置创建此用户的用户
//        employee.setCreateUser((Long) session.getAttribute("employee"));
//        employee.setUpdateUser((Long) session.getAttribute("employee"));

        //log.info(employee.toString());

        // 若添加的用户名 冲突, 则会抛出异常, 这里使用了GlobalExceptionHandler 来处理异常
        employeeService.save(employee);
        return Result.success("添加用户成功!");
    }


    /**
     * 员工信息的分页显示
     * @param page 分页的第几页
     * @param pageSize 每页显示的数量
     * @param name 员工的姓名
     * @return 分页查询后封装的数据(employeePage)
     */
    @GetMapping("/page")
    public Result<Page<Employee>> showEmployeeInfo(@RequestParam("page") Integer page, Integer pageSize, String name) {

        Page<Employee> employeePage = new Page<>(page, pageSize);
        // 使用lambda 条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        // 当name不为空时, 添加name添加模糊查询
        wrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);
        // 排序  
        wrapper.orderByAsc(Employee::getCreateTime);
        // 分页查询并将数据封装到 employeePage 中
        employeeService.page(employeePage, wrapper);

        // 使用普通的条件构造器
//        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
//        wrapper.like(StringUtils.isNotBlank(name), "name", name);
//        wrapper.orderByDesc("create_time");  // 这里无法使用驼峰法, 只能与数据库字段对应
//        employeeService.page(employeePage, wrapper);


        // log.info(employeePage.toString());
        return Result.success(employeePage);
    }


    /**
     * 员工信息更新(包括账号的状态)
     * @param employee 前端传递的参数
     * @return 处理的后返回json
     */
    @PutMapping
    public Result<String> updateEmployee(@RequestBody Employee employee) {

//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long) session.getAttribute("employee"));

        employeeService.updateById(employee);
        return Result.success("更新账号信息成功!");
    }


    /**
     * 根据id来查询employee信息
     * @param id 前端传递的id参数
     */
    @GetMapping("/{id}")
    public Result<Employee> getEmployeeInfoById(@PathVariable Long id) {

        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        }
        return Result.error("没有查询到此员工!");
    }


}
