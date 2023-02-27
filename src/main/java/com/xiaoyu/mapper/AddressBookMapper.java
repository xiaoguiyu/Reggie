package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022/10/17 14:40
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
*/

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




