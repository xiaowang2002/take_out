package com.atwzs.reggie.controller;

import com.atwzs.reggie.common.R;
import com.atwzs.reggie.domain.Category;
import com.atwzs.reggie.domain.Dish;
import com.atwzs.reggie.domain.Setmeal;
import com.atwzs.reggie.dto.SetmealDto;
import com.atwzs.reggie.service.CategoryService;
import com.atwzs.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName SetmealController
 * @Description
 * @Author WangZhisheng
 * @Date 18:50 2023/5/1
 * @Version 11.0.15
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> saveSetmeal(@RequestBody SetmealDto setmealDto) {
        setmealService.saveSetmealDto(setmealDto);
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Setmeal::getName, name);
        //排序
        wrapper.orderByAsc(Setmeal::getUpdateTime);

        setmealService.page(setmealPage, wrapper);

        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");

        List<Setmeal> records = setmealPage.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            //根据id查分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }


    @DeleteMapping
    public R<String> delete(String[] ids) {
        int index = 0;
        for (String id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            if (setmeal.getStatus() != 1) {
                setmealService.removeById(id);
            } else {
                index++;
            }
        }
        if (index > 0 && index == ids.length) {
            return R.error("选中的套餐均为启售状态，不能删除");
        } else {
            return R.success("删除成功");
        }
    }

    @PostMapping("/status/{status}")
    public R<String> sale(@PathVariable int status, String[] ids) {
        for (String id : ids) {
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }

    //根据Id查询套餐信息
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getSetmealDto(id);
        return R.success(setmealDto);
    }

    //修改套餐
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithDish(setmealDto);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
