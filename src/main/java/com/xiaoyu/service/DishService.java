package com.xiaoyu.service;

import com.xiaoyu.pojo.dto.DishDto;
import com.xiaoyu.pojo.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xiaoyu
* @createDate 2022-10-07 19:10
* @description 针对表【dish(菜品管理)】的数据库操作Service
*/

public interface DishService extends IService<Dish> {


    /**
     * 保存菜品和口味信息
     * @param dishDto 包含菜品和口味的数据
     */
    void saveDishAndDishFlavor(DishDto dishDto);


    /**
     * 根据id查询菜品信息和口味
     * @param id id
     */
    DishDto selectDishWithFlavor(Long id);


    /**
     * 根据菜品id更新菜品信息和口味
     * @param dishDto 菜品数据和口味数据
     */
    void updateDishWithFlavor(DishDto dishDto);


    /**
     * 根据id删除菜品信息和口味信息
     * @param listId 需要删除的id集合
     */
    void deleteDishWithFlavor(List<Long> listId);


    /**
     * 菜品的起售或停售
     * @param state 表示售卖的状态
     * @param listId 需要操作的菜品id
     */
    void startOrStopSale(Integer state, List<Long> listId);


}
