package com.xiaoyu.config;


import com.alibaba.fastjson.JSON;
import com.xiaoyu.common.BaseContext;
import com.xiaoyu.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/6 21:41
 * 自定义的登录拦截器
 */

@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("拦截到请求:" + request.getRequestURI());

        // 获取用户的session
        Object employee = request.getSession().getAttribute("employee");
        Object user = request.getSession().getAttribute("user");


        if (employee != null) {
            Long employeeId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeId);
            return true;
        }

        if (user != null) {
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            return true;
        }

        //log.info("处理拦截请求" + request.getRequestURI());
        // 将没有登录的用户拦截, 通过前端控制路由跳转到登录页面
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return false;
    }
}
