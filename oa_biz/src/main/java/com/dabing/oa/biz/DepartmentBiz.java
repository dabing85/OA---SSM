package com.dabing.oa.biz;

import com.dabing.oa.entity.Department;

import java.util.List;

public interface DepartmentBiz {
    //增
    void add(Department department);
    //删
    void remove(String sn);
    //改
    void edit(Department department);
    //查
    Department get(String sn);
    List<Department> getAll();
}
