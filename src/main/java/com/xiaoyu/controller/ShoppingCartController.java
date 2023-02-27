package com.xiaoyu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaoyu.common.BaseContext;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.entity.ShoppingCart;
import com.xiaoyu.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/17 22:52
 * 购物车请求处理
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;


    /**
     * 将菜品或套餐添加到购物车
     * @param shoppingCart 需要保存的信息
     * @return 返回处理后的数据
     */
    @PostMapping("/add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {


        // 获取并设置当前用户
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        // 查询当前用户的购物车是否有此套餐/菜品
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        // 添加的为菜品
        wrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        // 添加的为套餐
        wrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        // 如果有此菜品或套餐, 则设置数量加一, 如果没有, 则设置数量为1, 然后进行数据持久化
        ShoppingCart one = shoppingCartService.getOne(wrapper);
        if (one == null) {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
        } else {
            one.setNumber(one.getNumber() + 1);
            shoppingCartService.updateById(one);
            shoppingCart = one;
        }

        return Result.success(shoppingCart);
    }


    /**
     * 查询当前用户的购物侧
     * @return 返回查询的数据
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        wrapper.orderByAsc(ShoppingCart::getCreateTime);

        return Result.success(shoppingCartService.list(wrapper));
    }


    /**
     * 清空当前用户的购物车
     */
    @DeleteMapping("/clean")
    public Result<String> clean() {

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(wrapper);

        return Result.success("清除购物车成功!");
    }


    /**
     * 减少购物车中的套餐/菜品数量
     */
    @PostMapping("/sub")
    public Result<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        wrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        wrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        // 查询出当前用户购物车的菜品或套餐
        ShoppingCart one = shoppingCartService.getOne(wrapper);

        //  如果购物车中的套餐/菜品 数量大于1, 则让数量减1
        if (one.getNumber() > 1) {
            one.setNumber(one.getNumber() - 1);
            shoppingCartService.updateById(one);
        } else {
            //当数量等于1时, 移除菜品/套餐
            shoppingCartService.removeById(one);
            one = shoppingCart;
        }

        return Result.success(one);
    }



}
