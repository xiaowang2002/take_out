package com.atwzs.reggie.service;

import com.atwzs.reggie.domain.Setmeal;
import com.atwzs.reggie.dto.SetmealDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SetmealService extends IService<Setmeal> {
    void saveSetmealDto(SetmealDto setmealDto);

    SetmealDto getSetmealDto(Long id);

    void updateWithDish(SetmealDto setmealDto);
}
