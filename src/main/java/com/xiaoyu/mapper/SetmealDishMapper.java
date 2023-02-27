package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022/10/13 21:44
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
*/

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}




