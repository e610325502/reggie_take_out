package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1.将密码md5处理
        String password=employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.根据username查询
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp=employeeService.getOne(lambdaQueryWrapper);

        if(emp==null){
            return R.error("error");
        }

        //3.密码比对
        if(!emp.getPassword().equals(password)){
            return R.error("error");
        }

        //4.状态比对
        if(emp.getStatus()==0){
            return R.error("账号禁用");
        }

        //5,登录成功
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);


    }



    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的id
//        long empId=(long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);


        return R.success("成功了宝贝");
    }

    /*
    员工信息查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        Page pageInfo=new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //执行查询
        queryWrapper.orderByAsc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);



        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((long)request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");

    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id){
        Employee employee=employeeService.getById(id);
        return R.success(employee);
    }
}
