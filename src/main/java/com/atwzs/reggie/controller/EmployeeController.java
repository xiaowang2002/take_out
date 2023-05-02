package com.atwzs.reggie.controller;

import com.atwzs.reggie.common.R;
import com.atwzs.reggie.domain.Employee;
import com.atwzs.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.PackagePrivate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @ClassName EmployeeController
 * @Description
 * @Author WangZhisheng
 * @Date 8:02 2023/5/1
 * @Version 11.0.15
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {

        //将密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据页面提交的用户名来查数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        //判断
        if (emp == null) {
            return R.error("用户还未注册");
        }

        //对比密码，密码不一致，登录失败
        if (!Objects.equals(emp.getPassword(), password)) {
            return R.error("密码错误");
        }

        if (emp.getStatus() == 0) {
            return R.error("账号已被禁用");
        }

        //登录成功，用户id存入session
        httpServletRequest.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpSession session) {
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> saveEmployee(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        log.info("新增员工信息：{}", employee);
        //设置密码,md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

//        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        Page<Employee> employeePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        wrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        wrapper.orderByAsc(Employee::getUpdateTime);
        employeeService.page(employeePage, wrapper);
        return R.success(employeePage);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("employee={}", employee);
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id) {
        Employee emp = employeeService.getById(id);
        if (emp != null) {
            return R.success(emp);
        }
        return R.error("没有找到相关用户信息");
    }
}
