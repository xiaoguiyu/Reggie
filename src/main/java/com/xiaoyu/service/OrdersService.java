package com.xiaoyu.service;

import com.xiaoyu.pojo.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author xiaoyu
* @date 2022/10/18 01:34
* @description 针对表【orders(订单表)】的数据库操作Service
*/

public interface OrdersService extends IService<Orders> {


    /**
     * 订单处理
     * @param order 一些订单的数据
     */
    void order(Orders order);


}
