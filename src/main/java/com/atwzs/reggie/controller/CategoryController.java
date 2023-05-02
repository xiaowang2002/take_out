package com.atwzs.reggie.controller;

import com.atwzs.reggie.common.R;
import com.atwzs.reggie.domain.Category;
import com.atwzs.reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoryController
 * @Description
 * @Author WangZhisheng
 * @Date 12:55 2023/5/1
 * @Version 11.0.15
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("新增分类{}", category);
        categoryService.save(category);
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(categoryPage, wrapper);
        return R.success(categoryPage);
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("ids={}", ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    //修改分类
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("分类修改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null, Category::getType, type);
        //添加排序条件
        wrapper.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }
}
