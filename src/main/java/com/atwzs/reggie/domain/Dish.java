package com.atwzs.reggie.domain;

import com.atwzs.reggie.common.IdJsonSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName Dish
 * @Description
 * @Author WangZhisheng
 * @Date 13:19 2023/5/1
 * @Version 11.0.15
 */
@Data
public class Dish {

    @JsonSerialize(using = IdJsonSerializer.class)
    private Long id;

    //菜品名称
    private String name;

    //菜品分类id
    @JsonSerialize(using = IdJsonSerializer.class)
    private Long categoryId;

    //菜品价格
    private BigDecimal price;

    //商品码
    private String code;

    //图片
    private String image;

    //描述信息
    private String description;

    //0 停售 1 起售
    private Integer status;

    //顺序
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
