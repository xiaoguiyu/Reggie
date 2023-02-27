package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.pojo.entity.DishFlavor;
import com.xiaoyu.service.DishFlavorService;
import com.xiaoyu.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaoyu
* @date 2022/10/10 21:15
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
*/

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




