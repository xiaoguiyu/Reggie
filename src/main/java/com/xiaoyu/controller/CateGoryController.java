package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.entity.Category;
import com.xiaoyu.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/6 20:29
 */

@RestController
@RequestMapping("/category")
public class CateGoryController {

    @Resource  // 使用 @Resource 自动装配
    private CategoryService categoryService;


    /**
     * 添加套餐和菜品分类
     * @param category 前端传递的参数
     */
    @PostMapping
    public Result<String> addCategory(@RequestBody Category category) {

        //注意: 这里创建时间/更新时间/创建人/更新人 使用了 公共字段自动填充
        categoryService.save(category);
        return Result.success("添加分类成功!");
    }


    /**
     * 按照分页查询显示分类数据
     * @param page 分页的第几页
     * @param pageSize 每页显示的数量
     * @return 分页查询后封装的数据(categoryPage)
     */
    @GetMapping("/page")
    public Result<Page<Category>> showCategoryInfo(@RequestParam("page") Integer page, Integer pageSize) {

        Page<Category> categoryPage = new Page<>(page, pageSize);
        // lambda 条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 按照sort字段排序
        wrapper.orderByAsc(Category::getSort);
        // 分页查询
        categoryService.page(categoryPage, wrapper);
        return Result.success(categoryPage);
    }


    /**
     * 删除分类数据
     * @param id 菜品分类的id
     */
    @DeleteMapping
    public Result<String> deleteCategory(Long id) {

        categoryService.removeById(id);
        return Result.success("删除分类成功!");
    }

    /**
     * 根据id来查询分类信息, 以便在修改页面回显
     * @param id 前端传递的id
     * @return 查询到的数据
     */
    @GetMapping("{/id}")
    public Result<Category> getCategoryInfoById(@PathVariable Long id) {

        Category category = categoryService.getById(id);
        if (category != null) {
            return Result.success(category);
        }
        return Result.error("没有查询到分类信息");
    }


    @PutMapping
    public Result<String> updateCategory(@RequestBody Category category) {

        categoryService.updateById(category);
        return Result.success("更新分类成功!");
    }


    /**
     * 查询所有的菜品分类
     * @param category 分类的类型
     * @return list集合
     */
    @GetMapping("/list")
    public Result<List<Category>> queryCategoryType(Category category) {

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 等值查询, 查询所有分类中的菜品分类
        wrapper.eq(category.getType() != null, Category::getType, category.getType());
        wrapper.orderByAsc(Category::getSort).orderByAsc(Category::getCreateTime);

        List<Category> list = categoryService.list(wrapper);
        return Result.success(list);
    }





}
