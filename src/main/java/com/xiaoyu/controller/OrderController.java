package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyu.common.BaseContext;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.entity.Orders;
import com.xiaoyu.service.OrdersService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/18 1:36
 * 订单请求处理
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrdersService ordersService;


    /**
     * 订单处理
     * @param orders 订单数据
     */
    @PostMapping("/submit")
    public Result<String> order(@RequestBody Orders orders) {

        ordersService.order(orders);
        return Result.success("订单已完成!");
    }


    /**
     * 历史订单查询
     * @param page 分页的第几页
     * @param pageSize 每个页面大小
     * @return 返回分页数据
     */
    @GetMapping({"/userPage", "/page"})
    public Result<Page<Orders>> page(Integer page, Integer pageSize) {



        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        wrapper.orderByAsc(Orders::getOrderTime);


//        List<Orders> ordersList = ordersService.list(wrapper);
//        List<Long> listId = ordersList.stream().map(Orders::getId).collect(Collectors.toList());
//
//        // 根据订单号来查询详细的订单信息
//        Page<OrderDetail> orderDetailPage = new Page<>(page, pageSize);
//        LambdaQueryWrapper<OrderDetail> orderDetailWrapper = new LambdaQueryWrapper<>();
//        orderDetailWrapper.in(OrderDetail::getOrderId, listId);
//        orderDetailService.page(orderDetailPage, orderDetailWrapper);

        return Result.success(ordersService.page(pageInfo, wrapper));
    }




}
