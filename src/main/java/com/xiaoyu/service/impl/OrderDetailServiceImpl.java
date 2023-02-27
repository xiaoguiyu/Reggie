package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.pojo.entity.OrderDetail;
import com.xiaoyu.service.OrderDetailService;
import com.xiaoyu.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaoyu
* @date 2022/10/18 01:34
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
*/

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




