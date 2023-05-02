package com.atwzs.reggie.service;

import com.atwzs.reggie.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ClassName CategoryService
 * @Description
 * @Author WangZhisheng
 * @Date 12:52 2023/5/1
 * @Version 11.0.15
 */
public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
