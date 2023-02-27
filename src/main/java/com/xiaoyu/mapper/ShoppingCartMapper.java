package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022/10/17 22:50
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
*/

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




