package com.xiaoyu.mapper;

import com.xiaoyu.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xiaoyu
* @date 2022/10/15 15:53
* @description 针对表【user(用户信息)】的数据库操作Mapper
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

}




