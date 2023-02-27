package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.dto.SetmealDto;
import com.xiaoyu.pojo.entity.Category;
import com.xiaoyu.pojo.entity.Setmeal;
import com.xiaoyu.service.CategoryService;
import com.xiaoyu.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/13 22:17
 */

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Resource
    private SetmealService setmealService;

    @Resource
    private CategoryService categoryService;


    /**
     * 新增套餐
     * @param setmealDto 包含套餐的信息和套餐的菜品信息
     * @return 返回一个执行成功的信息
     */
    @PostMapping
    public Result<String> addSetmeal(@RequestBody SetmealDto setmealDto) {

        setmealService.addSetmeal(setmealDto);
        return Result.success("增加套餐成功!");
    }



    /**
     * 菜品信息的分页显示
     * @param page 分页的第几页
     * @param pageSize 每页显示的数量
     * @param name 套餐名称
     * @return 返回查询到的数据
     */
    @GetMapping("/page")
    public Result<Page<SetmealDto>> page(@RequestParam Integer page, Integer pageSize, String name) {

        Page<Setmeal> setmealPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Setmeal::getName, name);
        wrapper.orderByAsc(Setmeal::getCreateTime);
        setmealService.page(setmealPage, wrapper);

        Page<SetmealDto> dtoPage = new Page<>();
        BeanUtils.copyProperties(setmealPage, dtoPage, "records");


        // 得到 records(套餐数据的集合)
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> dtoRecords = records.stream().map(setmeal -> {

            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);

            // 根据每个套餐分类id来查询出套餐分类名, 并在dto中保存套餐分类名
            Category category = categoryService.getById(setmeal.getCategoryId());
            setmealDto.setCategoryName(category.getName());

            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(dtoRecords);
        return Result.success(dtoPage);
    }


    /**
     * 根据套餐的id来查询套餐信息, 以便在更新页面回显数据
     * @param id 套餐id
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> querySetmealInfoById(@PathVariable Long id) {

        return Result.success(setmealService.querySetmealWithDish(id));
    }


    /**
     * 修改套餐信息(包括套餐中的菜品)
     * @param dto 套餐数据和套餐中的菜品数据
     * @return 返回更新成功的信息
     */
    @PutMapping
    public Result<String> updateSetmealInfo(@RequestBody SetmealDto dto) {

        setmealService.updateSetmealWithDish(dto);
        return Result.success("更新套餐成功!");
    }


    /**
     * 套餐的起售或停售
     * @param state 表示售卖的状态
     * @param listId 需要操作的套餐id
     * @return 返回更新成功的信息
     */
    @PostMapping("/status/{state}")
    public Result<String> setStatus(@PathVariable Integer state, @RequestParam("ids") List<Long> listId) {

        setmealService.startOrStopSale(state, listId);
        return Result.success("更新状态成功!");
    }


    /**
     * 删除套餐
     * @param listId 套餐id的集合
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("ids") List<Long> listId) {

        setmealService.deleteSetmeal(listId);
        return Result.success("删除套餐成功!");
    }


    /**
     * 根据套餐的id和售卖状态查出数据
     * @param setmeal 封装了套餐id和售卖状态
     * @return 查询到的数据
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Setmeal setmeal) {

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        wrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        wrapper.orderByDesc(Setmeal::getCreateTime);

        return Result.success(setmealService.list(wrapper));
    }




}
