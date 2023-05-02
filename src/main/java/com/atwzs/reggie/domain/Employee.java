package com.atwzs.reggie.domain;

import com.atwzs.reggie.common.IdJsonSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Employee
 * @Description
 * @Author WangZhisheng
 * @Date 7:49 2023/5/1
 * @Version 11.0.15
 */
@Data
public class Employee {
    @JsonSerialize(using = IdJsonSerializer.class)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //fill表示是否自动填充，insert表示添加是自动填充，insert_update表示更新是自动填充
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
