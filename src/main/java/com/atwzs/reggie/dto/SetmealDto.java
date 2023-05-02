package com.atwzs.reggie.dto;

import com.atwzs.reggie.domain.Setmeal;
import com.atwzs.reggie.domain.SetmealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
