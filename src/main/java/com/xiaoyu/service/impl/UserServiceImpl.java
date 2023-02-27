package com.xiaoyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyu.pojo.entity.User;
import com.xiaoyu.service.UserService;
import com.xiaoyu.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author xiaoyu
* @date 2022/10/15 15:53
* @description 针对表【user(用户信息)】的数据库操作Service实现
*/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}




