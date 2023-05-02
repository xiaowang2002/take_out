package com.atwzs.reggie.dto;

import com.atwzs.reggie.domain.Dish;
import com.atwzs.reggie.domain.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
