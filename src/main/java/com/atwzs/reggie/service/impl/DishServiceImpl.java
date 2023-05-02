package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.common.R;
import com.atwzs.reggie.domain.Category;
import com.atwzs.reggie.domain.Dish;
import com.atwzs.reggie.domain.DishFlavor;
import com.atwzs.reggie.dto.DishDto;
import com.atwzs.reggie.mapper.DishMapper;
import com.atwzs.reggie.service.CategoryService;
import com.atwzs.reggie.service.DishFlavorService;
import com.atwzs.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private CategoryService categoryService;


    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存信息在基本信息表
        log.info("详情信息：{}", dishDto);
        this.save(dishDto);

        Long dishId = dishDto.getId();//id被回显

        //获取菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().peek((item) -> item.setDishId(dishId)).collect(Collectors.toList());

        //保存菜品口味到菜品数据表
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    @Transactional
    public DishDto getDishDtoById(Long id) {
        Dish dish = getById(id);   //查询菜品信息
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //查询口味信息
        //查询菜品口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);

        dishDto.setFlavors(list);

        Category category = categoryService.getById(dishDto.getCategoryId());

        dishDto.setCategoryName(category.getName());
        return dishDto;
    }

    @Override
    public void updateDishDto(DishDto dishDto) {
        updateById(dishDto);

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(wrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().peek((item) -> item.setDishId(dishDto.getId())).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
