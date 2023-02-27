package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.common.CustomException;
import com.xiaoyu.pojo.dto.SetmealDto;
import com.xiaoyu.pojo.entity.Setmeal;
import com.xiaoyu.pojo.entity.SetmealDish;
import com.xiaoyu.service.SetmealDishService;
import com.xiaoyu.service.SetmealService;
import com.xiaoyu.mapper.SetmealMapper;
import com.xiaoyu.utils.DeleteImg;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author xiaoyu
* @date 2022/10/07 19:17
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
*/

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{

    @Resource
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     * @param setmealDto 包含套餐的信息和套餐的菜品信息
     */
    @Override
    @Transactional      // 事务
    public void addSetmeal(SetmealDto setmealDto) {

        this.save(setmealDto);

        // 获取套餐的id, 这里因为刚保存了套餐, 因此会生成套餐id
        Long setmealId = setmealDto.getId();

        // 获取套餐中的菜品
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(dish -> {
            // 给每个菜品设置套餐id
            dish.setSetmealId(setmealId);
        });
        setmealDishService.saveBatch(setmealDishes);
    }


    /**
     * 根据套餐的id来查询套餐信息和套餐中的菜品, 以便在更新页面回显数据
     * @param id 套餐id
     */
    @Override
    public SetmealDto querySetmealWithDish(Long id) {

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(this.getById(id), setmealDto);

        // 根据套餐id查询出套餐中的菜品
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(wrapper);

        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }


    /**
     * 修改套餐信息(包括套餐中的菜品)
     * @param dto 套餐数据和套餐中的菜品数据
     */
    @Override
    @Transactional
    public void updateSetmealWithDish(SetmealDto dto) {

        this.updateById(dto);

        // 先删除套餐中原有的菜品
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, dto.getId());
        setmealDishService.remove(wrapper);

        List<SetmealDish> setmealDishes = dto.getSetmealDishes().stream().map(setmealDish -> {

            setmealDish.setSetmealId(dto.getId());
            return setmealDish;
        }).collect(Collectors.toList());

        // 添加套餐中更改的菜品
        setmealDishService.saveBatch(setmealDishes);


    }


    /**
     * 套餐的起售或停售
     * @param state 表示售卖的状态
     * @param listId 需要操作的套餐id
     */
    @Override
    @Transactional
    public void startOrStopSale(Integer state, List<Long> listId) {

        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(state);

        listId.forEach(id -> {
            setmeal.setId(id);
            this.updateById(setmeal);
        });
    }


    /**
     * 删除套餐
     * @param listId 套餐id的集合
     */
    @Override
    @Transactional
    public void deleteSetmeal(List<Long> listId) {

        // 查询用户选中正在售卖的套餐数量
        // select count(*) from setmeal where id in( ?, ?, ?) and status = 1
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.in(Setmeal::getId, listId);
        setmealWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(setmealWrapper);

        // 如果用户选择中的存在售卖的套餐这, 抛出一个业务异常
        if (count > 0) {
            throw new CustomException("选择的套餐在在售卖中, 无法删除!");
        }

        // 删除照片
        List<String> imgNameList = listId.stream().map(id -> {

            Setmeal setmeal = this.getById(id);
            return setmeal.getImage();
        }).collect(Collectors.toList());
        DeleteImg.deleteImg(imgNameList);


        this.removeByIds(listId);
        LambdaQueryWrapper<SetmealDish> setmealDishWrapper = new LambdaQueryWrapper<>();
        setmealDishWrapper.in(SetmealDish::getSetmealId, listId);
        setmealDishService.remove(setmealDishWrapper);
    }


}




