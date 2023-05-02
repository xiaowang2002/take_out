package com.atwzs.reggie.domain;

import com.atwzs.reggie.common.IdJsonSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName Category
 * @Description
 * @Author WangZhisheng
 * @Date 12:48 2023/5/1
 * @Version 11.0.15
 */
@Data
public class Category {

    @JsonSerialize(using = IdJsonSerializer.class)
    private Long id;

    //类型 1 菜品分类 2 套餐分类
    private Integer type;

    //分类名称
    private String name;

    //顺序
    private Integer sort;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    //修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
//
//    //是否删除
//    private Integer isDeleted;
}
