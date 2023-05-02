package com.atwzs.reggie.service;

import com.atwzs.reggie.domain.Dish;
import com.atwzs.reggie.dto.DishDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    DishDto getDishDtoById(Long id);

    void updateDishDto(DishDto dishDto);
}
