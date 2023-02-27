package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022/10/18 01:34
* @description 针对表【orders(订单表)】的数据库操作Mapper
*/

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




