package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.pojo.entity.ShoppingCart;
import com.xiaoyu.service.ShoppingCartService;
import com.xiaoyu.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaoyu
* @date 2022/10/17 22:50
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
*/

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{

}




