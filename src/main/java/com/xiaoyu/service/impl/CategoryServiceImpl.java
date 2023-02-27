package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.common.CustomException;
import com.xiaoyu.pojo.entity.Category;
import com.xiaoyu.pojo.entity.Dish;
import com.xiaoyu.pojo.entity.Setmeal;
import com.xiaoyu.service.CategoryService;
import com.xiaoyu.mapper.CategoryMapper;
import com.xiaoyu.service.DishService;
import com.xiaoyu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
* @author Administrator
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-10-06 20:26:42
*/

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 自定义的删除方法
     * @param id 需要删除的id
     */
    @Override
    public boolean removeById(Serializable id) {

        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, id);

        // 查询饭菜关联菜品分类的数量(通过id)
        int dishCount = dishService.count(dishWrapper);
        if (dishCount > 0) {
            // 如果饭菜 关联了菜品的分类, 则不会删除, 抛出一个业务异常
            throw new CustomException("当前分类关联了菜品, 无法删除!");
        }


        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId, id);

        // 查询套餐关联分类的数量
        int setmealCount = setmealService.count(setmealWrapper);
        if (setmealCount > 0) {
            // 如果套餐 关联了套餐分类, 则不会删除, 抛出一个业务异常
            throw new CustomException("当前分类关联了套餐, 无法删除!");
        }
        return super.removeById(id);
    }


}




