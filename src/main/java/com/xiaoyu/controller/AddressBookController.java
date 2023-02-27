package com.xiaoyu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xiaoyu.common.BaseContext;
import com.xiaoyu.common.Result;
import com.xiaoyu.pojo.entity.AddressBook;
import com.xiaoyu.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小鱼
 * @version 1.0
 * @date 2022/10/17 14:40
 * 地址簿请求处理
 */

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {


    @Resource
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public Result<AddressBook> save(@RequestBody AddressBook addressBook) {

        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public Result<AddressBook> setDefault(@RequestBody AddressBook addressBook) {

        log.info("addressBook:{}", addressBook);




        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();

        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL: update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);

        addressBook.setIsDefault(1);
        //SQL: update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return Result.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public Result<AddressBook> get(@PathVariable Long id) {

        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return Result.success(addressBook);
        }

        return Result.error("没有找到该对象");

    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public Result<AddressBook> getDefault() {

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) {
            return Result.error("没有找到该对象");
        }

        return Result.success(addressBook);
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(AddressBook addressBook) {

        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);

        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        //SQL: select * from address_book where user_id = ? order by update_time desc
        return Result.success(addressBookService.list(queryWrapper));
    }




}
