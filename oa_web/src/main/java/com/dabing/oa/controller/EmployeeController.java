package com.dabing.oa.controller;

import com.dabing.oa.biz.DepartmentBiz;
import com.dabing.oa.biz.EmployeeBiz;
import com.dabing.oa.entity.Employee;
import com.dabing.oa.global.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("employeeController")
@RequestMapping("/employee")  //设置统一的路径
public class EmployeeController {
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private DepartmentBiz departmentBiz;
    //查
    //向页面传递各个部门信息；不依赖于Spring MVC
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",employeeBiz.getAll());//把所有部门信息存储到list里
        return "employee_list";
    }
    //增
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("employee",new Employee());
        map.put("dlist",departmentBiz.getAll());
        map.put("plist", Constant.getPosts());
        return "employee_add";
    }

    @RequestMapping("/add")
    public String add(Employee employee){
        employeeBiz.add(employee);
        return "redirect:list";//不能直接转到jsp页面，因为信息还没更新，得转到list的路径下
    }

    //删
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        employeeBiz.remove(sn);
        return "redirect:list";//不能直接转到jsp页面，因为信息还没更新，得转到list的路径下
    }

    //改
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("employee",employeeBiz.get(sn));
        map.put("dlist",departmentBiz.getAll());
        map.put("plist", Constant.getPosts());
        return "employee_update";
    }
    @RequestMapping("/update")
    public String update(Employee employee){
        employeeBiz.edit(employee);
        return "redirect:list";//不能直接转到jsp页面，因为信息还没更新，得转到list的路径下
    }
}
