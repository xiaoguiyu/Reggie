package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.dto.DishDto;
import com.xiaoyu.pojo.entity.Category;
import com.xiaoyu.pojo.entity.Dish;
import com.xiaoyu.pojo.entity.DishFlavor;
import com.xiaoyu.service.CategoryService;
import com.xiaoyu.service.DishFlavorService;
import com.xiaoyu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/10 23:23
 */

@Slf4j
@RestController
@RequestMapping("/dish")
@SuppressWarnings({""})
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private DishFlavorService dishFlavorService;


    /**
     * 新增菜品
     * @param dishDto 包含菜品的信息和菜品口味的信息
     */
    @PostMapping
    public Result<String> addDish(@RequestBody DishDto dishDto) {

        log.info(dishDto.toString());

        dishService.saveDishAndDishFlavor(dishDto);
        return Result.success("新增菜品成功!");
    }


    /**
     * 菜品信息的分页显示
     * @param page 分页的第几页
     * @param pageSize 每页显示的数量
     * @param name 菜品名称
     * @return 返回查询到的数据
     */
    @GetMapping("/page")
    public Result<Page<DishDto>> page(Integer page, Integer pageSize, String name) {

        // 分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        // 创建lambda条件构造器
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Dish::getName, name);
        // 排序
        wrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getCreateTime);
        dishService.page(pageInfo, wrapper);

        // 这里因为dish里面没有菜品的分类名, 只有菜品分类id, 因此需要使用dishDto来解决这个问题
        Page<DishDto> dishDtoPage = new Page<>();
        // 将pageInfo的属性拷贝到dishDtoPage中, 并忽略records 属性
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<DishDto> dishDtoList = new ArrayList<>();

        // records 里面包含了查询出的所有数据
        List<Dish> records = pageInfo.getRecords();
        for (Dish record : records) {
            DishDto dishDto = new DishDto();

            // 将每个dish的属性copy到dishDto中
            BeanUtils.copyProperties(record, dishDto);

            // 通过菜品id查询到菜品分类名, 并保存
            Category category = categoryService.getById(record.getCategoryId());
            dishDto.setCategoryName(category.getName());

            dishDtoList.add(dishDto);
        }
        // 将处理后的数据放入dishDtoPage中
        dishDtoPage.setRecords(dishDtoList);

        return Result.success(dishDtoPage);
    }


    /**
     * 根据id查询菜品信息和口味(在更新页面回显数据)
     * @param id 菜品id
     * @return 返回菜品信息和口味信息
     */
    @GetMapping("/{id}")
    public Result<DishDto> queryDishInfoById(@PathVariable Long id) {

        return Result.success(dishService.selectDishWithFlavor(id));
    }


    /**
     * 根据菜品id更新菜品信息和口味
     * @param dishDto 菜品数据和口味数据
     */
    @PutMapping
    public Result<String> updateDishInfo(@RequestBody DishDto dishDto) {

        // log.info(dishDto.toString());
        dishService.updateDishWithFlavor(dishDto);
        return Result.success("更新菜品信息成功!");
    }


    /**
     * 根据id删除菜品信息和口味信息
     * @param listId 需要删除的id集合
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("ids") List<Long> listId) {

        dishService.deleteDishWithFlavor(listId);
        return Result.success("删除菜品成功!");
    }


    /**
     * 菜品的起售或停售
     * @param state 表示售卖的状态
     * @param listId 需要操作的菜品id
     */
    @PostMapping("/status/{state}")
    public Result<String> setStatus(@PathVariable Integer state, @RequestParam("ids") List<Long> listId) {

        dishService.startOrStopSale(state, listId);
        return Result.success("菜品状态操作成功!");
    }


    /**
     * 根据菜品分类的id来查询所有菜品
     * @param id 菜品分类id
     * @return 返回查询的信息
     */
    @GetMapping("/list")
    public Result<List<DishDto>> selectDishInfoById(@RequestParam("categoryId") Long id) {

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId, id);
        List<Dish> dishList = dishService.list(wrapper);


        // 获取菜品的口味
        List<DishDto> dishDtoList = dishList.stream().map(dish -> {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);

            // 通过菜品id查询到菜品分类名, 并保存
            Category category = categoryService.getById(dish.getCategoryId());
            dishDto.setCategoryName(category.getName());

            LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
            dishFlavorWrapper.eq(DishFlavor::getDishId, dish.getId());
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorWrapper);
            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());

        return Result.success(dishDtoList);
    }









}
