package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.domain.DishFlavor;
import com.atwzs.reggie.dto.DishDto;
import com.atwzs.reggie.mapper.DishFlavorMapper;
import com.atwzs.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
