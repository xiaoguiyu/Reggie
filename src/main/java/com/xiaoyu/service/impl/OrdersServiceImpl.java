package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.common.BaseContext;
import com.xiaoyu.common.CustomException;
import com.xiaoyu.pojo.entity.*;
import com.xiaoyu.service.*;
import com.xiaoyu.mapper.OrdersMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author xiaoyu
* @date 2022/10/18 01:34
* @description 针对表【orders(订单表)】的数据库操作Service实现
*/

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService{

    @Resource
    private AddressBookService addressBookService;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private UserService userService;


    /**
     * 订单处理
     */
    @Override
    @Transactional
    public void order(Orders order) {

        // 获取当前用户id, 并根据id查询当前用户的信息
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);

        // 根据order 中的地址id来查询出 送货地址
        AddressBook addressBook = addressBookService.getById(order.getAddressBookId());
        if (addressBook == null) {
            throw new CustomException("送货地址为空, 不能下单!");
        }

        // 查询当前用户的购物车
        LambdaQueryWrapper<ShoppingCart> shoppingCartWrapper = new LambdaQueryWrapper<>();
        shoppingCartWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartWrapper);

        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("购物车没有货物, 不能下单!");
        }

        // 生成订单号
        long orderId = IdWorker.getId();

        // 计算金额的对象, 可以在多线程,高并发中使用, 保证计算的安全性
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetailList = shoppingCartList.stream().map(item -> {

            // 设置订单详情的数据
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            // 计算购物车中的金额总数
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        // 设置订单的数据
        order.setId(orderId);
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(LocalDateTime.now());
        order.setStatus(2);
        order.setAmount(new BigDecimal(amount.get())); //总金额
        order.setUserId(userId);
        order.setNumber(String.valueOf(orderId));
        order.setUserName(user.getName());
        order.setConsignee(addressBook.getConsignee());
        order.setPhone(addressBook.getPhone());
        order.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));


        // 保存订单数据, 一条数据
        this.save(order);

        // 保存详细的订单数据 orderDetail, 多条数据
        orderDetailService.saveBatch(orderDetailList);

        // 清空购物车
        shoppingCartService.remove(shoppingCartWrapper);
    }



}




