package com.atwzs.reggie.mapper;

import com.atwzs.reggie.domain.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName EmployeeMapper
 * @Description
 * @Author WangZhisheng
 * @Date 7:59 2023/5/1
 * @Version 11.0.15
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
