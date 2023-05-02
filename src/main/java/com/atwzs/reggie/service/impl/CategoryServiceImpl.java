package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.common.R;
import com.atwzs.reggie.domain.Category;
import com.atwzs.reggie.domain.Dish;
import com.atwzs.reggie.domain.Setmeal;
import com.atwzs.reggie.exception.CustomException;
import com.atwzs.reggie.mapper.CategoryMapper;
import com.atwzs.reggie.service.CategoryService;
import com.atwzs.reggie.service.DishService;
import com.atwzs.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName CategoryServiceImpl
 * @Description
 * @Author WangZhisheng
 * @Date 12:53 2023/5/1
 * @Version 11.0.15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private SetmealService setmealService;

    @Resource
    private DishService dishService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getCategoryId, id);
        long count = dishService.count(wrapper);
        if (count > 0) {
            throw new CustomException("已经关联了菜品,不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealWrapper.eq(Setmeal::getCategoryId, id);
        long count2 = setmealService.count(setmealWrapper);

        if (count2 > 0) {
            //已经关联套餐，抛出业务异常
            throw new CustomException("已经关联套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
