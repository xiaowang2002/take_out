package com.atwzs.reggie.mapper;

import com.atwzs.reggie.domain.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}