package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.common.CustomException;
import com.xiaoyu.pojo.dto.DishDto;
import com.xiaoyu.pojo.entity.Dish;
import com.xiaoyu.pojo.entity.DishFlavor;
import com.xiaoyu.service.DishFlavorService;
import com.xiaoyu.service.DishService;
import com.xiaoyu.mapper.DishMapper;
import com.xiaoyu.utils.DeleteImg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author xiaoyu
* @createDate 2022-10-07 19:10
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
*/

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;


    @Override
    @Transactional  // 使用事务来保证数据的一致性
    public void saveDishAndDishFlavor(DishDto dishDto) {
        // 保存菜品的基本信息
        this.save(dishDto);

        // 获取菜品的id 注意: 这里因为将数据刚保存了dish表中, 因此可以获取到id
        Long dishId = dishDto.getId();

        // 获取菜品的口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            // 给每个口味绑定菜品id
            flavor.setDishId(dishId);
        }

        dishFlavorService.saveBatch(flavors);
    }


    /**
     * 根据id查询菜品信息和口味
     * @param id 菜品id
     * @return 返回菜品信息和口味信息
     */
    @Override
    public DishDto selectDishWithFlavor(Long id) {

        // 查询菜品信息
        Dish dish = this.getById(id);
        // 将信息保存在dishDto中
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        // 根据菜品id来查询对应的口味信息
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> dishFlavorList = dishFlavorService.list(wrapper);

        // 将口味信息保存到dishDto中
        dishDto.setFlavors(dishFlavorList);

        return dishDto;
    }


    /**
     * 根据菜品id更新菜品信息和口味
     * @param dishDto 菜品数据和口味数据
     */
    @Override
    @Transactional   // 使用事务保证数据的一致性
    public void updateDishWithFlavor(DishDto dishDto) {

        this.updateById(dishDto);

        // 先删除在添加
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(wrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            // 给每个口味绑定菜品id
            flavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(flavors);
    }


    /**
     * 根据id删除菜品信息和口味信息
     * @param listId 需要删除的id集合
     */
    @Override
    @Transactional  // 使用事务保证数据的一致性
    public void deleteDishWithFlavor(List<Long> listId) {

        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.in(Dish::getId, listId);
        dishWrapper.eq(Dish::getStatus, 1);
        int count = this.count(dishWrapper);
        // 如果用户选择中的存在正在售卖的菜品, 则抛出一个业务异常
        if (count > 0) {
            throw new CustomException("选择的菜品正在售卖中, 无法删除!");
        }


        List<String> imgNameList = listId.stream().map(id -> {

            Dish dish = this.getById(id);
            return dish.getImage();
        }).collect(Collectors.toList());
        DeleteImg.deleteImg(imgNameList);


        this.removeByIds(listId);
        LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
        dishFlavorWrapper.in(DishFlavor::getDishId, listId);
        dishFlavorService.remove(dishFlavorWrapper);
    }


    /**
     * 菜品的起售或停售
     * @param state 表示售卖的状态
     * @param listId 需要操作的菜品id
     */
    @Override
    @Transactional
    public void startOrStopSale(Integer state, List<Long> listId) {

        Dish dish = new Dish();
        dish.setStatus(state);

        listId.forEach(id -> {
            dish.setId(id);
            this.updateById(dish);
        });




    }


}




