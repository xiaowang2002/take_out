package com.atwzs.reggie.mapper;

import com.atwzs.reggie.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description
 * @Author WangZhisheng
 * @Date 20:51 2023/5/1
 * @Version 11.0.15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
