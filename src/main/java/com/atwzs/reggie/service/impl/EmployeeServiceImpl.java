package com.atwzs.reggie.service.impl;

import com.atwzs.reggie.domain.Employee;
import com.atwzs.reggie.mapper.EmployeeMapper;
import com.atwzs.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName EmployeeServiceImpl
 * @Description
 * @Author WangZhisheng
 * @Date 8:00 2023/5/1
 * @Version 11.0.15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
