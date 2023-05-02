package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.domain.User;
import com.atwzs.reggie.mapper.UserMapper;
import com.atwzs.reggie.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description
 * @Author WangZhisheng
 * @Date 20:50 2023/5/1
 * @Version 11.0.15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
