package com.atwzs.reggie.controller;

import com.atwzs.reggie.common.R;
import com.atwzs.reggie.domain.Category;
import com.atwzs.reggie.domain.Dish;
import com.atwzs.reggie.domain.DishFlavor;
import com.atwzs.reggie.dto.DishDto;
import com.atwzs.reggie.service.CategoryService;
import com.atwzs.reggie.service.DishFlavorService;
import com.atwzs.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(name), Dish::getName, name);
        wrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(dishPage, wrapper);

        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

        List<Dish> records = dishPage.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(dishDto.getCategoryId());
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        DishDto dishDto = dishService.getDishDtoById(id);
        return R.success(dishDto);
    }

    //修改菜品
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateDishDto(dishDto);
        return R.success("修改菜品成功");
    }

    //停售起售菜品
    @PostMapping("/status/{status}")
    public R<String> sale(@PathVariable int status,
                          String[] ids) {
        for (String id : ids) {
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }

    //删除菜品
    @DeleteMapping
    public R<String> delete(String[] ids) {
        for (String id : ids) {
            dishService.removeById(id);
        }
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<DishDto>> list(Long categoryId) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getStatus, 1);
        wrapper.eq(categoryId != null, Dish::getCategoryId, categoryId);
        wrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(wrapper);

        //改造一下，因为需要返回更多的数据，如口味，和dishid
        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);
//            Long categoryId2 = item.getCategoryId();
//            //根据id查分类对象
//            Category category = categoryService.getById(categoryId2);
//            if (category != null) {
//                String categoryName = category.getName();
//                dishDto.setCategoryName(categoryName);
//            }

            //当前菜品id
            Long dishId = item.getId();
//            dishDto.setId(dishId);  已经通过beanutils.copyProperties传过了
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, dishId);
            //SQL: select* from dishflavor where dish_id=?;
            List<DishFlavor> dishFlavorlist = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavorlist);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

}
