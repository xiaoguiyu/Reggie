package com.xiaoyu.service;

import com.xiaoyu.pojo.dto.SetmealDto;
import com.xiaoyu.pojo.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xiaoyu
* @date 2022/10/07 19:17
* @description 针对表【setmeal(套餐)】的数据库操作Service
*/

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐
     * @param setmealDto 包含套餐的信息和套餐的菜品信息
     */
    void addSetmeal(SetmealDto setmealDto);


    /**
     * 根据套餐的id来查询套餐信息和套餐中的菜品, 以便在更新页面回显数据
     * @param id 套餐id
     */
    SetmealDto querySetmealWithDish(Long id);


    /**
     * 修改套餐信息(包括套餐中的菜品)
     * @param dto 套餐数据和套餐中的菜品数据
     */
    void updateSetmealWithDish(SetmealDto dto);


    /**
     * 套餐的起售或停售
     * @param state 表示售卖的状态
     * @param listId 需要操作的套餐id
     */
    void startOrStopSale(Integer state, List<Long> listId);


    /**
     * 删除套餐
     * @param listId 套餐id的集合
     */
    void deleteSetmeal(List<Long> listId);

}
