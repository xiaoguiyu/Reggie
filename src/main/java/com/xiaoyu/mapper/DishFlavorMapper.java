package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022/10/10 21:15
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
*/

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




