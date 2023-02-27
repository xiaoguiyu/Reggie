package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.pojo.entity.AddressBook;
import com.xiaoyu.service.AddressBookService;
import com.xiaoyu.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaoyu
* @date 2022/10/17 14:40
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
*/

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService{

}




