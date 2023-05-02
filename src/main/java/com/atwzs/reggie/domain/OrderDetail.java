package com.atwzs.reggie.domain;

import com.atwzs.reggie.common.IdJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
public class OrderDetail {

    @JsonSerialize(using = IdJsonSerializer.class)
    private Long id;

    //名称
    private String name;

    //订单id
    @JsonSerialize(using = IdJsonSerializer.class)
    private Long orderId;


    //菜品id
    @JsonSerialize(using = IdJsonSerializer.class)
    private Long dishId;


    //套餐id
    @JsonSerialize(using = IdJsonSerializer.class)
    private Long setmealId;


    //口味
    private String dishFlavor;


    //数量
    private Integer number;

    //金额
    private BigDecimal amount;

    //图片
    private String image;
}
