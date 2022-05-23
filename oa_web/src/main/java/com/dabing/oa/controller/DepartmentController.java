package com.dabing.oa.controller;

import com.dabing.oa.biz.DepartmentBiz;
import com.dabing.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("departmentController")
@RequestMapping("/department")  //设置统一的路径
public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;
    //查
    //向页面传递各个部门信息；不依赖于Spring MVC
    @RequestMapping("/list")
    public String list(Map<String,Object> map){
        map.put("list",departmentBiz.getAll());//把所有部门信息存储到list里
        return "department_list";//转发到department_list.jsp页面去;在spring-web.xml里配置过前缀后缀
    }
    //增
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("department",new Department());
        return "department_add";//转发到department_add.jsp页面去;在spring-web.xml里配置过前缀后缀
    }

    @RequestMapping("/add")
    public String add(Department department){
        departmentBiz.add(department);
        return "redirect:list";//不能直接转到jsp页面，因为信息还没更新，得转到list的路径下
    }

    //删
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        departmentBiz.remove(sn);
        return "redirect:list";//不能直接转到jsp页面，因为信息还没更新，得转到list的路径下
    }

    //改
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("department",departmentBiz.get(sn));
        return "department_update";//转发到department_add.jsp页面去;在spring-web.xml里配置过前缀后缀
    }
    @RequestMapping("/update")
    public String update(Department department){
        departmentBiz.edit(department);
        return "redirect:list";//不能直接转到jsp页面，因为信息还没更新，得转到list的路径下
    }
}
