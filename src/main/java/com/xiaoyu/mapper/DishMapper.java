package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022-10-07 19:10
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
*/

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




