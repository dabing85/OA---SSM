package com.dabing.oa.biz;

import com.dabing.oa.entity.Employee;

import java.util.List;

public interface EmployeeBiz {
    //增
    void add(Employee employee);
    //删
    void remove(String sn);
    //改
    void edit(Employee employee);
    //查
    Employee get(String sn);
    List<Employee> getAll();
}
