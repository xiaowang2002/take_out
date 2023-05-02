package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.domain.ShoppingCart;
import com.atwzs.reggie.mapper.ShoppingCartMapper;
import com.atwzs.reggie.service.ShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Description
 * @Author WangZhisheng
 * @Date 10:54 2023/5/2
 * @Version 11.0.15
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
